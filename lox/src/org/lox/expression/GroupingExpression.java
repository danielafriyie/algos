package org.lox.expression;

import org.lox.token.Token;

public class GroupingExpression extends Expression {
    public final Expression expression;

    public GroupingExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitGroupingExpression(this);
    }
}
