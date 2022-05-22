package expression.generic;

public class GenericInteger implements GenericOperation<Integer> {
    @Override
    public Integer add(Integer firstArg, Integer secondArg) {
        return (firstArg + secondArg);
    }

    @Override
    public Integer subtract(Integer firstArg, Integer secondArg) {
        return (firstArg - secondArg);
    }

    @Override
    public Integer multiply(Integer firstArg, Integer secondArg) {
        return (firstArg * secondArg);
    }

    @Override
    public Integer divide(Integer firstArg, Integer secondArg) {
        return (firstArg / secondArg);
    }

    @Override
    public Integer minus(Integer argument) {
        return -argument;
    }

    @Override
    public Integer getValue(int value) {
        return value;
    }
}
