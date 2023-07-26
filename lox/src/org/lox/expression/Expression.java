package org.lox.expression;

import org.lox.token.Token;

public abstract class Expression {

    public abstract <R> R accept(Visitor<R> visitor);
}
