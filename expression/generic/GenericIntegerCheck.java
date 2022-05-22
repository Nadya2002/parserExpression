package expression.generic;


import expression.exceptions.ParseException;

public class GenericIntegerCheck implements GenericOperation<Integer> {
    @Override
    public Integer add(Integer firstArg, Integer secondArg) {
        if ((firstArg >= 0 && secondArg > Integer.MAX_VALUE - firstArg) || (
                firstArg < 0 && secondArg < Integer.MIN_VALUE - firstArg)) {
            throw new ParseException("overflow");
        }
        return firstArg + secondArg;
    }

    @Override
    public Integer subtract(Integer firstArg, Integer secondArg) {
        if (secondArg >= 0 && firstArg < Integer.MIN_VALUE + secondArg ||
                secondArg < 0 && firstArg > Integer.MAX_VALUE + secondArg) {
            throw new ParseException("overflow");
        }
        return firstArg - secondArg;
    }

    @Override
    public Integer multiply(Integer firstArg, Integer secondArg) {
        if (hasOverflow(firstArg, secondArg)) {
            throw new ParseException("overflow");
        }
        return firstArg * secondArg;
    }

    @Override
    public Integer divide(Integer firstArg, Integer secondArg) {
        if (secondArg == 0) {
            throw new ParseException("division by zero");
        } else if (firstArg == Integer.MIN_VALUE && secondArg == -1) {
            throw new ParseException("overflow");
        }

        return firstArg / secondArg;
    }

    @Override
    public Integer minus(Integer argument) {
        if (argument == Integer.MIN_VALUE) {
            throw new ParseException("overflow in negate class");
        }
        return -argument;
    }

    @Override
    public Integer getValue(int value) {
        return value;
    }

    static boolean hasOverflow(int a, int b) {
        if (a == 0 || b == 0) {
            return false;
        } else if (a > 0 && b > 0) {
            return a > Integer.MAX_VALUE / b;
        } else if (b < 0 && a < 0) {
            return a < Integer.MAX_VALUE / b;
        } else {
            if (b > 0) {
                return a < Integer.MIN_VALUE / b;
            } else {
                return b < Integer.MIN_VALUE / a;
            }
        }
    }
}
