package expression.generic;

public class GenericDouble implements GenericOperation<Double> {
    @Override
    public Double add(Double firstArg, Double secondArg) {
        return firstArg + secondArg;
    }

    @Override
    public Double subtract(Double firstArg, Double secondArg) {
        return firstArg - secondArg;
    }

    @Override
    public Double multiply(Double firstArg, Double secondArg) {
        return firstArg * secondArg;
    }

    @Override
    public Double divide(Double firstArg, Double secondArg) {
        return firstArg / secondArg;
    }

    @Override
    public Double minus(Double argument) {
        return -argument;
    }

    @Override
    public Double getValue(int value) {
        return (double) value;
    }
}
