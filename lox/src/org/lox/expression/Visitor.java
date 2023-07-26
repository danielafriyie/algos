package org.lox.expression;

import org.lox.token.Token;

public interface Visitor<R> {

    R visitBinaryExpression(BinaryExpression expression);

    R visitGroupingExpression(GroupingExpression expression);

    R visitLiteralExpression(LiteralExpression expression);

    R visitUnaryExpression(UnaryExpression expression);
}
