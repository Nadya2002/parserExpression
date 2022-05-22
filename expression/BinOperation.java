package expression;

import expression.generic.GenericOperation;

public abstract class BinOperation<T> implements MyExpression<T>, MyTripleExpression<T> {
    private MyExpression<T> firstArgument, secondArgument;
    protected String operation;
    private MyTripleExpression<T> oneArg, twoArg;

    public BinOperation(MyExpression<T> firstArgument, MyExpression<T> secondArgument) {
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
    }

    public T evaluate(T variableValue, GenericOperation<T> operation) {
        return calculation(firstArgument.evaluate(variableValue, operation),
                secondArgument.evaluate(variableValue, operation), operation);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("(");
        str.append(firstArgument.toString() + " ");
        str.append(operation + " ");
        str.append(secondArgument.toString());
        str.append(")");
        return str.toString();
    }

    public abstract T calculation(T oneArgument, T twoArgument, GenericOperation<T> operation);

    public boolean equals(Object obj) {
        if (obj != null && obj.hashCode() == this.hashCode()) {
            if (obj instanceof BinOperation) {
                return firstArgument.equals(((BinOperation) obj).firstArgument) &&
                        secondArgument.equals(((BinOperation) obj).secondArgument);
            }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return firstArgument.hashCode() - secondArgument.hashCode() * (int) (operation.charAt(0));
    }

    @Override
    public T evaluate(T x, T y, T z, GenericOperation<T> operation) {
        if (firstArgument instanceof MyTripleExpression) {
            oneArg = (MyTripleExpression) firstArgument;
        }

        if (secondArgument instanceof MyTripleExpression) {
            twoArg = (MyTripleExpression) secondArgument;
        }
        return calculation(oneArg.evaluate(x, y, z, operation),
                twoArg.evaluate(x, y, z, operation), operation);
    }
}
