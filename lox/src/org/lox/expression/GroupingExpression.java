package org.lox.expression;

import org.lox.token.Token;

public class GroupingExpression extends Expression {
    private final Expression expression;

    public GroupingExpression(Expression expression) {
        this.expression = expression;
    }
}
