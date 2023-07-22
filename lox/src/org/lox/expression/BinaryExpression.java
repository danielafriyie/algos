package org.lox.expression;

import org.lox.token.Token;

public class BinaryExpression extends Expression {
    private final Expression left;
    private final Expression right;
    private final Token operator;

    public BinaryExpression(Expression left, Expression right, Token operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
}
