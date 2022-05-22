package expression;

import expression.generic.GenericOperation;

public class Const<T> implements MyExpression<T>, MyTripleExpression<T> {
    private final int value;

    public Const(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public T evaluate(T variableValue, GenericOperation<T> operation) {
        return operation.getValue(value);
    }

    @Override
    public T evaluate(T x, T y, T z, GenericOperation<T> operation) {
        return operation.getValue(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.hashCode() == this.hashCode()) {
            if (obj instanceof Const) {
                Const that = (Const) obj;
                return that.getValue() == this.getValue();
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getValue();
    }
}
