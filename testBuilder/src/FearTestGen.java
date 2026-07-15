import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FearTestGen — generate .fear test files from annotated source files.
 * Usage: java FearTestGen <input.fear>
 *
 * Test class naming: [ClassName][ClassGenerics]Test[Cap][MethodName][MethodGenerics]A[ArgCount]
 * Operator symbols mapped to English ALLCAPS per character: > → GT, = → EQUALS, etc.
 * Capabilities: omitted/imm → Imm, mut → Mut, read → Read
 */
public class FearTestGen {

  record MethodTests(String testClassName, String codeBlock) {}
  record ClassTests(String className, int classGenericCount, List<MethodTests> methods) {
    String suiteClassName() {
      return className + (classGenericCount > 0 ? classGenericCount : "") + "Tests";
    }
  }

  // ── Operator → English (single character, no longest-match needed) ───────────

  private static final Map<Character, String> OPS = new HashMap<>();
  static {
    OPS.put('+', "PLUS");
    OPS.put('-', "MINUS");
    OPS.put('*', "STAR");
    OPS.put('/', "SLASH");
    OPS.put('%', "PERCENT");
    OPS.put('^', "CARET");
    OPS.put('&', "AMP");
    OPS.put('|', "PIPE");
    OPS.put('~', "TILDE");
    OPS.put('<', "LT");
    OPS.put('>', "GT");
    OPS.put('=', "EQUALS");
    OPS.put('!', "BANG");
    OPS.put('?', "QUESTION");
    OPS.put('@', "AT");
    OPS.put('#', "HASH");
    OPS.put('$', "DOLLAR");
  }

  static String methodToName(String raw) {
    if (!raw.isEmpty() && (Character.isLetterOrDigit(raw.charAt(0)) || raw.charAt(0) == '_'))
      return capitalise(raw);
    StringBuilder sb = new StringBuilder();
    for (char c : raw.toCharArray())
      sb.append(OPS.getOrDefault(c, String.valueOf(c)));
    return sb.toString();
  }

  // ── Parsing ─────────────────────────────────────────────────────────────────

  private static final Pattern CLASS_HEADER = Pattern.compile(
    "^(\\w[\\w\\[\\], /]*?)\\s*:[^\\n{]+\\{", Pattern.MULTILINE);

  private static final Pattern GENERIC_CONTENT = Pattern.compile("\\[([^\\]]*)]");

  // Captures: group 1 = cap, group 2 = word method, group 3 = operator,
  //           group 4 = method generics [...], group 5 = arg list (...)
  private static final Pattern METHOD_DECL = Pattern.compile(
    "^(mut|read|imm)?\\s*(?:\\.(\\w+)|([+\\-*/%^&|~<>=!?@#$]{1,3}))(\\[[^\\]]*])?(\\([^)]*\\))?",
    Pattern.CASE_INSENSITIVE);

  static int countGenerics(String content) {
    if (content == null || content.isBlank()) return 0;
    int depth = 0, commas = 0;
    for (char c : content.toCharArray()) {
      if      (c == '[') depth++;
      else if (c == ']') depth--;
      else if (c == ',' && depth == 0) commas++;
    }
    return commas + 1;
  }

  /** Count top-level comma-separated parameters inside (...) */
  static int countArgs(String parenContent) {
    if (parenContent == null || parenContent.isBlank()) return 0;
    int depth = 0, commas = 0;
    for (char c : parenContent.toCharArray()) {
      if      (c == '(' || c == '[') depth++;
      else if (c == ')' || c == ']') depth--;
      else if (c == ',' && depth == 0) commas++;
    }
    return commas + 1;
  }

  static String stripGenerics(String name) {
    int b = name.indexOf('[');
    return b >= 0 ? name.substring(0, b).trim() : name.trim();
  }

  static String capitalise(String s) {
    return s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
  }

  static String testClassName(
      String className, int classG, String cap, String method, int methodG, int argCount) {
    String capPart = (cap == null || cap.equalsIgnoreCase("imm")) ? "Imm" : capitalise(cap);
    return className + (classG > 0 ? classG : "") + "Test"
      + capPart + methodToName(method)
      + (methodG > 0 ? methodG : "")
      + "A" + argCount;
  }

  static String extractBody(String source, int after) {
    int depth = 1, i = after;
    while (i < source.length() && depth > 0) {
      char c = source.charAt(i++);
      if (c == '{') depth++; else if (c == '}') depth--;
    }
    return source.substring(after, i - 1);
  }

  static List<ClassTests> parse(String source) {
    List<ClassTests> results = new ArrayList<>();
    Matcher hm = CLASS_HEADER.matcher(source);

    while (hm.find()) {
      String rawName   = hm.group(1).trim();
      String className = stripGenerics(rawName);
      int    classG    = countGenerics(
        GENERIC_CONTENT.matcher(rawName).results().map(m -> m.group(1)).findFirst().orElse(""));
      String body      = extractBody(source, hm.end());

      List<MethodTests> methods = new ArrayList<>();
      List<String> blockLines   = new ArrayList<>();
      boolean inBlock           = false;

      for (String line : body.lines().toList()) {
        String s = line.strip();
        boolean isDocLine = s.startsWith("//>") || s.startsWith("//-");

        if (isDocLine) {
          blockLines.add(line);
          inBlock = true;
        } else {
          if (inBlock && !s.startsWith("///")) {
            Matcher mm = METHOD_DECL.matcher(s);
            if (mm.find() && !blockLines.isEmpty()) {
              String cap    = mm.group(1);
              String method = mm.group(2) != null ? mm.group(2) : mm.group(3);
              String gb     = mm.group(4);
              String ab     = mm.group(5);
              int methodG   = gb != null ? countGenerics(gb.substring(1, gb.length() - 1)) : 0;
              int argCount  = ab != null ? countArgs(ab.substring(1, ab.length() - 1)) : 0;
              String testName = testClassName(className, classG, cap, method, methodG, argCount);
              methods.add(new MethodTests(testName, String.join("\n", blockLines)));
            }
            blockLines.clear();
            inBlock = false;
          } else if (!isDocLine) {
            blockLines.clear();
            inBlock = false;
          }
        }
      }

      if (!methods.isEmpty())
        results.add(new ClassTests(className, classG, methods));
    }
    return results;
  }

  // ── Generation ───────────────────────────────────────────────────────────────

  static String generate(String stem, List<ClassTests> classes) {
    StringBuilder sb = new StringBuilder();

    sb.append(stem).append("UnitTests: UnitTests{::\n");
    for (var c : classes) sb.append("  .test ").append(c.suiteClassName()).append("\n");
    sb.append("  }\n");

    for (var ct : classes) {
      sb.append(ct.suiteClassName()).append(": Test{::\n");
      for (var m : ct.methods()) sb.append("  .test ").append(m.testClassName()).append("\n");
      sb.append("  }\n");

      for (var m : ct.methods()) {
        String raw = m.codeBlock();
        Matcher indentM = Pattern.compile("^(\\s*)//- *\\{::").matcher(raw);
        String indent = indentM.find() ? Pattern.quote(indentM.group(1)) : "";
        String block = raw
          .replaceFirst("(?m)^" + indent + "//- *\\{::", m.testClassName() + ": Test{::")
          .replaceAll ("(?m)^" + indent + "//> ?",       "  ")
          .replaceFirst("(?m)^" + indent + "//- *\\}\\s*$", "  }");
        sb.append(block).append("\n");
      }
    }
    return sb.toString();
  }

  // ── Entry point ──────────────────────────────────────────────────────────────

  public static void main(String[] args) throws IOException {
    if (args.length < 1) { System.err.println("Usage: java FearTestGen <input.fear>"); System.exit(1); }
    Path path     = Path.of(args[0]);
    String source = Files.readString(path);
    String name   = path.getFileName().toString();
    String stem   = capitalise(name.contains(".") ? name.substring(0, name.lastIndexOf('.')) : name);
    List<ClassTests> classes = parse(source);
    if (classes.isEmpty()) { System.err.println("No doc-tests found."); System.exit(0); }
    System.out.print(generate(stem, classes));
  }
}
