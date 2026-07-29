/**
 * Generates Fear doc-tests from //> comments in .fear source files.
 *
 * Each method with //> doc-tests gets its own test class named:
 *   [ClassName][ClassGenericCount][RefCap][MethodGenericCount][MethodName][NumArgs]Test
 *
 * e.g. Set[E], read .union[K](by: OrderHashBy[E,K], other: Set[E]): Set[E]
 *   -> Set1Read1Union2Test
 *
 * Operator encoding:
 *   <=> Cmp  | ++ PlusPlus | -- DashDash | == EqEq | != NotEq
 *   <= Le    | >= Ge       | <  Lt       | >  Gt
 *   +  Plus  | -  Dash     | &  And      | *  Star | /  Slash | #  Hash
 *
 * Output is written to stdout.
 */
// -----------------------------------------------------------------------
// Entry point
// -----------------------------------------------------------------------

void main(String[] args) throws IOException {
    if (args.length == 0) {
        System.err.println("Usage: FearDocTestGenerator <file.fear>");
        System.exit(1);
    }
    String src = Files.readString(Path.of(args[0]));
    String fileName = baseName(Path.of(args[0]).getFileName().toString());
    IO.print(generate(src, fileName));
}

        // -----------------------------------------------------------------------
        // Top-level generation
        // -----------------------------------------------------------------------

        static String generate(String src, String fileName) {
            List<ClassInfo> classes = parseClasses(src);
            StringBuilder sb = new StringBuilder();

            List<String> classAggNames = new ArrayList<>();
            for (ClassInfo ci : classes) {
                List<MethodInfo> documented = ci.methods.stream()
                        .filter(m -> !m.docLines.isEmpty())
                        .toList();
                if (documented.isEmpty()) continue;

                List<String> methodTestNames = new ArrayList<>();
                for (MethodInfo m : documented) {
                    String testName = m.testClassName(ci);
                    methodTestNames.add(testName);
                    sb.append(testName).append(": Test {::\n");
                    for (String line : m.docLines) {
                        sb.append("  ").append(line).append("\n");
                    }
                    sb.append("  }\n\n");
                }

                String aggName = ci.aggregatorName();
                classAggNames.add(aggName);
                sb.append(aggName).append(": Test {::\n");
                for (String name : methodTestNames) {
                    sb.append("  .test ").append(name).append("\n");
                }
                sb.append("  }\n\n");
            }

            // File-level aggregator only needed when more than one class has tests
            if (classAggNames.size() > 1) {
                String fileAgg = toTitleCase(fileName) + "Tests";
                sb.append(fileAgg).append(": Test {::\n");
                for (String name : classAggNames) {
                    sb.append("  .test ").append(name).append("\n");
                }
                sb.append("  }\n");
            }

            return sb.toString();
        }

        // -----------------------------------------------------------------------
        // Data model
        // -----------------------------------------------------------------------

        static class ClassInfo {
            String name;
            int genericCount;
            List<MethodInfo> methods = new ArrayList<>();

            String aggregatorName() {
                return name + genericCount + "ImmTests";
            }
        }

        static class MethodInfo {
            String refCap;            // "Imm" | "Read" | "Mut" | "Iso" | "Recov"
            String rawName;           // ".size", "++", "#", etc.
            int methodGenericCount;
            int numArgs;
            List<String> docLines = new ArrayList<>();

            String testClassName(ClassInfo ci) {
                return ci.name
                        + ci.genericCount
                        + refCap
                        + methodGenericCount
                        + encodeName(rawName)
                        + numArgs
                        + "Test";
            }
        }

        // -----------------------------------------------------------------------
        // Operator / identifier encoding
        // -----------------------------------------------------------------------

        // Ordered longest-first so "<=>" beats "<=" etc.
        private static final String[][] OPERATORS = {
                {"<=>", "Cmp"},
                {"++", "PlusPlus"},
                {"--", "DashDash"},
                {"==", "EqEq"},
                {"!=", "NotEq"},
                {"<=", "Le"},
                {">=", "Ge"},
                {"<", "Lt"},
                {">", "Gt"},
                {"+", "Plus"},
                {"-", "Dash"},
                {"&", "And"},
                {"*", "Star"},
                {"/", "Slash"},
                {"#", "Hash"},
        };

        static String encodeName(String raw) {
            String name = raw.startsWith(".") ? raw.substring(1) : raw;
            for (String[] entry : OPERATORS) {
                if (name.equals(entry[0])) return entry[1];
            }
            if (name.isEmpty()) return "Unknown";
            return Character.toUpperCase(name.charAt(0)) + name.substring(1);
        }

        // -----------------------------------------------------------------------
        // Parsing
        // -----------------------------------------------------------------------

        // Top-level class declaration (no leading whitespace).
        // Group 1: class name (may start with underscores)
        // Group 2: generic params inside [...] (may be null)
        static final Pattern CLASS_HEADER = Pattern.compile(
                "^(_*[A-Z][A-Za-z0-9_]*)(?:\\[([^\\]]+)])?\\s*:.*\\{\\s*$"
        );

        // Method declaration (must be indented — methods are never at column 0).
        // Group 1: optional ref-cap keyword
        // Group 2: method name (dot-prefixed identifier, bare operator, or #)
        // Group 3: optional method-level generics e.g. [K] or [K, K0]
        // Group 4: optional parameter list contents e.g. "by: Foo, other: Bar"
        static final Pattern METHOD_DECL = Pattern.compile(
                "^\\s+"
                        + "(?:(read|mut|iso|recov)\\s+)?"
                        + "(\\.?[A-Za-z_][A-Za-z0-9_$]*|[+\\-&*/<>=!]{1,3}|#)"
                        + "(?:\\[([^\\]]+)])?"
                        + "(?:\\(([^)]*)\\))?"
                        + "\\s*:"
                        + ".*$"
        );

        static List<ClassInfo> parseClasses(String src) {
            List<ClassInfo> result = new ArrayList<>();
            String[] lines = src.split("\n", -1);

            int i = 0;
            while (i < lines.length) {
                Matcher cm = CLASS_HEADER.matcher(lines[i]);
                if (!cm.matches()) {
                    i++;
                    continue;
                }

                ClassInfo ci = new ClassInfo();
                ci.name = cm.group(1);
                String generics = cm.group(2);
                ci.genericCount = generics == null ? 0 : countCommas(generics) + 1;

                int depth = charCount(lines[i], '{') - charCount(lines[i], '}');
                i++;

                List<String> pendingDoc = new ArrayList<>();

                while (i < lines.length && depth > 0) {
                    String line = lines[i];
                    depth += charCount(line, '{') - charCount(line, '}');

                    String trimmed = line.stripLeading();

                    if (trimmed.startsWith("//>")) {
                        String content = trimmed.substring(3);
                        if (!content.isEmpty() && content.charAt(0) == ' ') content = content.substring(1);
                        pendingDoc.add(content);
                        i++;
                        continue;
                    }

                    // Only attempt method matching while we're still inside the class body
                    if (depth > 0) {
                        Matcher mm = METHOD_DECL.matcher(line);
                        if (mm.matches()) {
                            MethodInfo mi = new MethodInfo();
                            String cap = mm.group(1);
                            mi.refCap = cap == null ? "Imm" : toTitleCase(cap);
                            mi.rawName = mm.group(2);
                            String mGen = mm.group(3);
                            mi.methodGenericCount = mGen == null ? 0 : countCommas(mGen) + 1;
                            mi.numArgs = countArgs(mm.group(4));
                            mi.docLines.addAll(pendingDoc);
                            pendingDoc.clear();
                            ci.methods.add(mi);
                            i++;
                            continue;
                        }
                    }

                    // Non-doc, non-method line: if pending docs weren't claimed by a
                    // method they belong to a default/derived body — discard them only
                    // when we hit a clearly non-method structural line (blank, closing
                    // brace already handled by depth, etc.). Keep accumulating otherwise
                    // so multi-line signatures don't lose their docs.
                    if (trimmed.isEmpty() || trimmed.startsWith("///") || trimmed.startsWith("//")) {
                        // keep pendingDoc — doc comment or blank before the actual decl
                    } else if (!trimmed.startsWith("//>")) {
                        // A real code line that isn't a method decl: discard orphaned docs
                        pendingDoc.clear();
                    }

                    i++;
                }

                if (!ci.methods.isEmpty()) result.add(ci);
            }
            return result;
        }

        // -----------------------------------------------------------------------
        // Utilities
        // -----------------------------------------------------------------------

        /** Count top-level (bracket-depth-0) commas. */
        static int countCommas(String s) {
            int depth = 0, count = 0;
            for (char c : s.toCharArray()) {
                if (c == '[' || c == '(') depth++;
                else if (c == ']' || c == ')') depth--;
                else if (c == ',' && depth == 0) count++;
            }
            return count;
        }

        /** Count formal arguments in a parameter-list string. */
        static int countArgs(String params) {
            if (params == null || params.isBlank()) return 0;
            return countCommas(params) + 1;
        }

        static int charCount(String s, char c) {
            int n = 0;
            for (char x : s.toCharArray()) if (x == c) n++;
            return n;
        }

        static String baseName(String fileName) {
            int dot = fileName.lastIndexOf('.');
            return dot < 0 ? fileName : fileName.substring(0, dot);
        }

        /** "read" -> "Read", "mut" -> "Mut" */
        static String toTitleCase(String s) {
            if (s == null || s.isEmpty()) return s;
            return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
        }