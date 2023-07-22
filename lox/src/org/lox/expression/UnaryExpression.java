package org.lox.expression;

import org.lox.token.Token;

public class UnaryExpression extends Expression {
    private final Token operator;
    private final Expression right;

    public UnaryExpression(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }
}
