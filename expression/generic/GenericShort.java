package expression.generic;

public class GenericShort implements GenericOperation<Short> {
    @Override
    public Short add(Short firstArg, Short secondArg) {
        return (short) (firstArg + secondArg);
    }

    @Override
    public Short subtract(Short firstArg, Short secondArg) {
        return (short) (firstArg - secondArg);
    }

    @Override
    public Short multiply(Short firstArg, Short secondArg) {
        return (short) (firstArg * secondArg);
    }

    @Override
    public Short divide(Short firstArg, Short secondArg) {
        return (short) (firstArg / secondArg);
    }

    @Override
    public Short minus(Short argument) {
        return (short) (-argument);
    }

    @Override
    public Short getValue(int value) {
        return (short) value;
    }
}
