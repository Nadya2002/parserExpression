package expression;

import expression.generic.GenericOperation;

public abstract class UnarnOperation<T> implements MyExpression<T>, MyTripleExpression<T> {
    private MyExpression<T> argument;
    protected String operation;
    private MyTripleExpression<T> arg;

    public UnarnOperation(MyExpression<T> argument) {
        this.argument = argument;
    }

    public T evaluate(T variableValue, GenericOperation<T> operation) {
        return calculation(argument.evaluate(variableValue, operation), operation);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(operation + " ");
        str.append(argument.toString());
        return str.toString();
    }

    public abstract T calculation(T argument, GenericOperation<T> operation);

    public boolean equals(Object obj) {
        if (obj != null && obj.hashCode() == this.hashCode()) {
            if (obj instanceof UnarnOperation) {
                return argument.equals(((UnarnOperation) obj).argument);
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return argument.hashCode() * (int) (operation.charAt(0));
    }

    @Override
    public T evaluate(T x, T y, T z, GenericOperation<T> operation) {
        if (argument instanceof MyTripleExpression) {
            arg = (MyTripleExpression) argument;
        }

        return calculation(arg.evaluate(x, y, z, operation), operation);
    }
}
