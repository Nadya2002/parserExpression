package expression.generic;

public interface GenericOperation<T> {
    T add(T firstArg, T secondArg);

    T subtract(T firstArg, T secondArg);

    T multiply(T firstArg, T secondArg);

    T divide(T firstArg, T secondArg);

    T minus(T argument);

    T getValue(int value);
}
