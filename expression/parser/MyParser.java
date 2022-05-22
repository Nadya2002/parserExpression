package expression.parser;

import expression.MyTripleExpression;

public interface MyParser<T> {
    MyTripleExpression<T> parse(String expression);
}
