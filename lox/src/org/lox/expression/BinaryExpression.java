package org.lox.expression;

import org.lox.token.Token;

public class BinaryExpression extends Expression {
    private final Expression left;
    private final Token operator;
    private final Expression right;

    public BinaryExpression(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
}
