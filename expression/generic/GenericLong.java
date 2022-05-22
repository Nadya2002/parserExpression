package expression.generic;

public class GenericLong implements GenericOperation<Long> {
    @Override
    public Long add(Long firstArg, Long secondArg) {
        return firstArg + secondArg;
    }

    @Override
    public Long subtract(Long firstArg, Long secondArg) {
        return firstArg - secondArg;
    }

    @Override
    public Long multiply(Long firstArg, Long secondArg) {
        return firstArg * secondArg;
    }

    @Override
    public Long divide(Long firstArg, Long secondArg) {
        return firstArg / secondArg;
    }

    @Override
    public Long minus(Long argument) {
        return -argument;
    }

    @Override
    public Long getValue(int value) {
        return (long) value;
    }
}
