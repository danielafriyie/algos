package org.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.nio.charset.StandardCharsets;

public class GenerateAST {
    private static final String separator = File.pathSeparator;

    private static void defineType(String outputDir, String baseName, String className, String fields)
            throws IOException {
        String path = outputDir + separator + className + ".java";
        try (PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8)) {
            writer.println("package org.lox.expression;");
            writer.println();
            writer.println("import java.util.List;");
            writer.println();
            writer.println(String.format("public class %s extends %s {", className, baseName));
            String[] split = fields.split(",");
            for (String f : split) {
                writer.println(String.format("    private final %s;", f));
            }
            writer.println();
            writer.println(String.format("    public %s(%s) {", className, fields));
            for (String f : split) {
                String name = f.split(" ")[1].strip();
                writer.write(String.format("        this.%s = %s;", name, name));
            }
            writer.write("    }");
        }
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + separator + baseName + ".java";
        try (PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8)) {
            writer.println("package org.lox.expression;");
            writer.println();
            writer.println("import java.util.List;");
            writer.println();
            writer.println(String.format("public abstract class %s {", baseName));
            writer.println("}");
        }

        for (String type : types) {
            String[] split = type.split(":");
            String className = split[0].strip();
            String fields = split[1].strip();
            defineType(outputDir, baseName, className, fields);
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }
        String outputDir = args[0];
        defineAst(outputDir, "Expression", Arrays.asList(
                "BinaryExpression : Expression left, Token operator, Expression right",
                "GroupingExpression : Expression expression",
                "LiteralExpression : Object value",
                "UnaryExpression : Token operator, Expression right"
        ));
    }
}
