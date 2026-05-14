import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

/// AI SLOP WARNING
/// This should do the following:
/// 1. Read one or more .fear files specified as command-line arguments.
/// 2. For each file, parse its contents to find top-level class declarations
/// 3. For each class, extract doc-tests from comments that start with //>
///   - Note that a single test may span multiple consecutive //> lines,
///     and a semicolon at the end of a line indicates the end of a test.
/// 4. Generate a test suite in the specified format,
///   with a top-level dispatcher called <FileName>FileTests.
///    - Where each class with tests gets its own test suite called <ClassName>Tests.
/// WARNINGS:
///   - This is generated code and is likely not be robust.
///   - This will likely break if ";" exist inside a test body
///   - This will likely break if "//>" appears inside a test body
///   - Unlikely to handle nested classes.
public class FearTestGenerator {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Usage: FearTestGenerator <file1.fear> [file2.fear ...]");
            System.exit(1);
        }
        for (String arg : args) {
            Path input = Path.of(arg);
            String output = generate(input);
            System.out.println(output);
        }
    }

    static String generate(Path path) throws IOException {
        String src = Files.readString(path);
        String fileName = baseName(path.getFileName().toString());
        String fileClass = toTitleCase(fileName)+"File";

        List<ClassInfo> classes = parseClasses(src);

        StringBuilder sb = new StringBuilder();

        // Top-level dispatcher
        sb.append(fileClass).append("Tests: F[Tests, Tests] {::\n");
        for (ClassInfo ci : classes) {
            if (!ci.tests.isEmpty()) {
                sb.append("  .testSuite ").append(ci.name).append("Tests\n");
            }
        }
        sb.append("  }\n");

        // Per-class test suites
        for (ClassInfo ci : classes) {
            if (ci.tests.isEmpty()) continue;
            sb.append("\n");
            sb.append(ci.name).append("Tests: F[Tests, Tests] {::\n");
            for (String test : ci.tests) {
                // Indent each line of the test body by 2 extra spaces
                String indented = indentBody(test);
                sb.append("  .test(").append(indented).append(")\n");
            }
            sb.append("  }\n");
        }

        return sb.toString();
    }

    // -----------------------------------------------------------------------
    // Parsing
    // -----------------------------------------------------------------------

    /**
     * Holds the name of a top-level class/trait and all doc-tests found
     * within its body.
     */
    static class ClassInfo {
        final String name;
        final List<String> tests = new ArrayList<>();

        ClassInfo(String name) { this.name = name; }
    }

    /**
     * Splits the source into top-level class blocks and extracts //> tests
     * from each block's comments.

     * Strategy:
     *  1. Find each top-level declaration header line (Name: ... {).
     *  2. Collect all lines until the matching closing brace (tracking depth).
     *  3. Within those lines, collect consecutive //> comment lines into tests,
     *     splitting on semicolons that appear at the end of a //> line.
     */
    static List<ClassInfo> parseClasses(String src) {
        List<ClassInfo> result = new ArrayList<>();
        String[] lines = src.split("\n", -1);

        // Pattern: a top-level class/trait declaration.
        // Matches lines like:   SomeName[...]: SomeThing {
        // The name must start at column 0 (no leading whitespace).
        Pattern headerPattern = Pattern.compile(
                "^(_*[A-Z][A-Za-z0-9_]*)(?:\\[[^]]*])?\\s*:.*\\{\\s*$");

        int i = 0;
        while (i < lines.length) {
            Matcher m = headerPattern.matcher(lines[i]);
            if (m.matches()) {
                String className = m.group(1);
                // Collect the body of this class (track brace depth)
                int depth = countOpenBraces(lines[i]) - countCloseBraces(lines[i]);
                int start = i;
                i++;
                while (i < lines.length && depth > 0) {
                    depth += countOpenBraces(lines[i]);
                    depth -= countCloseBraces(lines[i]);
                    i++;
                }
                // lines[start..i-1] is the full class block
                String[] block = Arrays.copyOfRange(lines, start, i);
                ClassInfo ci = new ClassInfo(className);
                ci.tests.addAll(extractTests(block));
                result.add(ci);
            } else {
                i++;
            }
        }
        return result;
    }

    /**
     * Extracts individual test strings from a block of source lines.

     * Consecutive //> lines form a single test, unless a line (after stripping
     * the //> prefix and trimming) ends with a semicolon — that semicolon ends
     * one test and the next //> line starts a new one.

     * A trailing semicolon on the last line of a run also terminates a test
     * (and that test is included without the semicolon).
     */
    static List<String> extractTests(String[] lines) {
        List<String> tests = new ArrayList<>();
        // Each element is one logical line from a //> comment (semicolons stripped)
        List<String> current = new ArrayList<>();

        for (String raw : lines) {
            String trimmed = raw.stripLeading();
            if (!trimmed.startsWith("//>")) {
                // A non-comment line flushes whatever we were accumulating
                if (!current.isEmpty()) {
                    tests.add(joinTestLines(current));
                    current.clear();
                }
                continue;
            }

            // Strip the "//>" prefix (and one optional space)
            String content = trimmed.substring(3);
            if (content.startsWith(" ")) content = content.substring(1);

            // A semicolon at the end of the content terminates a test
            if (content.endsWith(";")) {
                current.add(content.substring(0, content.length() - 1));
                tests.add(joinTestLines(current));
                current.clear();
            } else {
                current.add(content);
            }
        }

        // Flush any remaining accumulation (no trailing semicolon needed on last test)
        if (!current.isEmpty()) {
            tests.add(joinTestLines(current));
        }

        return tests;
    }

    /**
     * Joins multi-line test fragments.
     * The first line is kept as-is; subsequent lines are indented by 2 spaces
     * (they represent continuation lines in the original source).
     */
    static String joinTestLines(List<String> lines) {
        if (lines.size() == 1) return lines.getFirst();
        StringBuilder sb = new StringBuilder(lines.getFirst());
        for (int i = 1; i < lines.size(); i++) {
            sb.append("\n  ").append(lines.get(i));
        }
        return sb.toString();
    }

    // -----------------------------------------------------------------------
    // Formatting helpers
    // -----------------------------------------------------------------------

    /**
     * Indents the body of a test for placement inside .test(...).
     * The first line is NOT indented (it follows the opening paren on the
     * same line).  Subsequent lines get 4 spaces so they sit neatly inside
     * the surrounding two-space indented .test(...) call.
     */
    static String indentBody(String body) {
        String[] parts = body.split("\n", -1);
        if (parts.length == 1) return body;
        StringBuilder sb = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            sb.append("\n    ").append(parts[i]);
        }
        return sb.toString();
    }

    // -----------------------------------------------------------------------
    // Utility
    // -----------------------------------------------------------------------

    static int countOpenBraces(String line)  { return count(line, '{'); }
    static int countCloseBraces(String line) { return count(line, '}'); }

    static int count(String s, char c) {
        int n = 0;
        for (char x : s.toCharArray()) if (x == c) n++;
        return n;
    }

    /** Returns the filename without its extension. */
    static String baseName(String fileName) {
        int dot = fileName.lastIndexOf('.');
        return dot < 0 ? fileName : fileName.substring(0, dot);
    }

    /**
     * Converts a snake_case or kebab-case file name to TitleCase.
     * "my_set" → "MySet", "set-utils" → "SetUtils", "set" → "Set".
     */
    static String toTitleCase(String name) {
        String[] parts = name.split("[_\\-]+");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (part.isEmpty()) continue;
            sb.append(Character.toUpperCase(part.charAt(0)));
            sb.append(part.substring(1));
        }
        return sb.toString();
    }
}
