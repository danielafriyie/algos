package org.lox.expression;

import org.lox.token.Token;

public class LiteralExpression extends Expression {
    public final Object value;

    public LiteralExpression(Object value) {
        this.value = value;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitLiteralExpression(this);
    }
}
