package expression;

import expression.generic.GenericOperation;

public interface MyTripleExpression<T> extends ToMiniString {
    T evaluate(T x, T y, T z, GenericOperation<T> operation);
    boolean equals(Object obj);
}
