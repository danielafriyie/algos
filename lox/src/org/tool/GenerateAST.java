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
                writer.println(String.format("    private final %s;", f.strip()));
            }
            writer.println();
            writer.println(String.format("    public %s(%s) {", className, fields));
            for (String f : split) {
                String name = f.strip().split(" ")[1].strip();
                writer.println(String.format("        this.%s = %s;", name, name));
            }
            writer.println("    }");
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
            writer.println("}");
        }

        for (String type : types) {
            String[] split = type.split(":");
            String className = split[0].strip();
            String fields = split[1].strip();
            defineType(outputDir, baseName, className, fields);
        }
    }

    private static String input(String query, Scanner scanner) {
        System.out.print(query);
        String in = scanner.nextLine();
        return in.strip();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String packageInput = input("Enter package name: ", scanner);
        String importInput = input("Enter import classes separated by (;): ", scanner);
        String outputDir = input("Enter output dir: ", scanner);
        if (outputDir.equals("")) {
            System.exit(64);
        }

        packageName = packageInput.strip().equals("") ? "" : packageInput.strip() + ";";
        List<String> importList = new ArrayList<>();
        Arrays.asList(importInput.split(";")).forEach((String s) -> {
            importList.add(s.strip() + ";");
        });
        imports = String.join("\n", importList);

        defineAst(outputDir, "Expression", Arrays.asList(
                "BinaryExpression : Expression left, Token operator, Expression right",
                "GroupingExpression : Expression expression",
                "LiteralExpression : Object value",
                "UnaryExpression : Token operator, Expression right"
        ));
    }
}
