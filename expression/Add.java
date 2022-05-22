package expression;

import expression.generic.GenericOperation;

public class Add<T> extends BinOperation<T> implements MyExpression<T> {
    public Add(MyExpression<T> firstArgument, MyExpression<T> secondArgument) {
        super(firstArgument, secondArgument);
        operation = "+";
    }

    @Override
    public T calculation(T oneArgument, T twoArgument, GenericOperation<T> operation) {
        return operation.add(oneArgument, twoArgument);
    }

}
