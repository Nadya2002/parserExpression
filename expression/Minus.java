package expression;

import expression.generic.GenericOperation;

public class Minus<T> extends UnarnOperation<T> {

    public Minus(MyExpression<T> argument) {
        super(argument);
        operation = "-";
    }

    @Override
    public T calculation(T argument, GenericOperation<T> operation) {
        return operation.minus(argument);
    }
}
