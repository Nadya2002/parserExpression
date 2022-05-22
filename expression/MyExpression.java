package expression;

import expression.generic.GenericOperation;

public interface MyExpression<T> extends ToMiniString {
    T evaluate(T variableValue, GenericOperation<T> operation);

    boolean equals(Object obj);
}
