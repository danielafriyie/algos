package org.lox.expression;

import org.lox.token.Token;
import org.lox.token.TokenType;

public class ASTPrinter implements Visitor<String> {

    private String parenthesize(String name, Expression... exprs) {
        StringBuilder builder = new StringBuilder();
        builder.append("(").append(name);
        for (Expression expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");
        return builder.toString();
    }

    public String print(Expression expr) {
        return expr.accept(this);
    }

    @Override
    public String visitBinaryExpression(BinaryExpression expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpression(GroupingExpression expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpression(LiteralExpression expr) {
        if (expr.value == null)
            return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpression(UnaryExpression expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }

    public static void main(String[] args) {
        Expression expression = new BinaryExpression(
                new UnaryExpression(new Token(TokenType.MINUS, "-", null, 1), new LiteralExpression(123)),
                new Token(TokenType.STAR, "*", null, 1),
                new GroupingExpression(new LiteralExpression(45.67))
        );

        System.out.println(new ASTPrinter().print(expression));
    }
}
