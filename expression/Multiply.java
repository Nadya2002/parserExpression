package expression;

import expression.generic.GenericOperation;

public class Multiply<T> extends BinOperation<T> implements MyExpression<T> {

    public Multiply(MyExpression<T> firstArgument, MyExpression<T> secondArgument) {
        super(firstArgument, secondArgument);
        operation = "*";
    }

    @Override
    public T calculation(T oneArgument, T twoArgument, GenericOperation<T> operation) {
        return operation.multiply(oneArgument, twoArgument);
    }

}
