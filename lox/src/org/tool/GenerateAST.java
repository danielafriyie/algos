package org.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.nio.charset.StandardCharsets;

public class GenerateAST {
    private static String packageName = "";
    private static String imports = "";
    private static final String separator = File.separator;

    private static void defineType(String outputDir, String baseName, String className, String fields)
            throws IOException {
        String path = outputDir + separator + className + ".java";
        try (PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8)) {
            writer.println(packageName);
            writer.println();
            writer.println(imports);
            writer.println();
            writer.println(String.format("public class %s extends %s {", className, baseName));
            String[] split = fields.split(",");
            for (String f : split) {
                writer.println(String.format("    private final %s;", f.strip()));  // fields
            }
            writer.println();
            writer.println(String.format("    public %s(%s) {", className, fields));  // constructor
            for (String f : split) {
                String name = f.strip().split(" ")[1].strip();
                writer.println(String.format("        this.%s = %s;", name, name));
            }
            writer.println("    }");
            writer.println();
            writer.println("    @Override");
            writer.println("    public <R> R accept(Visitor<R> visitor) {");  // override visitor method
            writer.println(String.format("        return visitor.visit%s(this);", className));
            writer.println("    }");
            writer.println("}");
        }
    }

    private static void defineVisitor(String outputDir, List<String> methods)
            throws IOException {
        String path = outputDir + separator + "Visitor" + ".java";
        try (PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8)) {
            writer.println(packageName);
            writer.println();
            writer.println(imports);
            writer.println();
            writer.println("public interface Visitor<R> {");
            writer.println();
            writer.println(String.join("\n\n", methods));
            writer.println("}");
        }
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + separator + baseName + ".java";
        try (PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8)) {
            writer.println(packageName);
            writer.println();
            writer.println(imports);
            writer.println();
            writer.println(String.format("public abstract class %s {", baseName));
            writer.println();
            writer.println("    public abstract <R> R accept(Visitor<R> visitor);");  // abstract method (accept)
            writer.println();
            writer.println("}");
        }

        List<String> visitorMethods = new ArrayList<>();
        for (String type : types) {
            String[] split = type.split(":");
            String className = split[0].strip();
            String fields = split[1].strip();
            defineType(outputDir, baseName, className, fields);
            visitorMethods.add(String.format("    R visit%s(%s %s);", className, className, baseName.toLowerCase()));
        }

        defineVisitor(outputDir, visitorMethods);
    }

    private static String input(String query, Scanner scanner) {
        System.out.print(query);
        String in = scanner.nextLine();
        return in.strip();
    }

    public static void main(String[] args) throws IOException {
        String outputDir = "C:\\Users\\afriy\\Desktop\\PROJECTS\\algos\\lox\\src\\org\\lox\\expression";
        packageName = "package org.lox.expression;";
        imports = "import org.lox.token.Token;";

        defineAst(outputDir, "Expression", Arrays.asList(
                "BinaryExpression : Expression left, Token operator, Expression right",
                "GroupingExpression : Expression expression",
                "LiteralExpression : Object value",
                "UnaryExpression : Token operator, Expression right"
        ));
    }
}
