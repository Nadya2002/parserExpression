package expression;

import expression.generic.GenericOperation;

public class Variable<T> implements MyExpression<T>, MyTripleExpression<T> {
    private final String variable;

    public Variable(String variable) {
        this.variable = variable;
    }

    String getVariable() {
        return variable;
    }

    @Override
    public String toString() {
        return getVariable();
    }

    @Override
    public T evaluate(T variableValue, GenericOperation<T> operation) {
        return variableValue;
    }

    @Override
    public T evaluate(T x, T y, T z, GenericOperation<T> operation) {
        if (variable.equals("x")) {
            return x;
        }

        if (variable.equals("y")) {
            return y;
        }

        if (variable.equals("z")) {
            return z;
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.hashCode() == this.hashCode()) {
            if (obj instanceof Variable) {
                Variable that = (Variable) obj;
                return that.getVariable().equals(this.getVariable());
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return variable.charAt(0);
    }
}
