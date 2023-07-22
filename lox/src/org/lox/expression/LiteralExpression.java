package org.lox.expression;

import org.lox.token.Token;

public class LiteralExpression extends Expression {
    private final Object value;

    public LiteralExpression(Object value) {
        this.value = value;
    }
}
