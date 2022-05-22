package expression.generic;

import java.math.BigInteger;

public class GenericBigInt implements GenericOperation<BigInteger> {
    @Override
    public BigInteger add(BigInteger firstArg, BigInteger secondArg) {
        return firstArg.add(secondArg);
    }

    @Override
    public BigInteger subtract(BigInteger firstArg, BigInteger secondArg) {
        return firstArg.subtract(secondArg);
    }

    @Override
    public BigInteger multiply(BigInteger firstArg, BigInteger secondArg) {
        return firstArg.multiply(secondArg);
    }

    @Override
    public BigInteger divide(BigInteger firstArg, BigInteger secondArg) {
        return firstArg.divide(secondArg);
    }

    @Override
    public BigInteger minus(BigInteger argument) {
        return argument.multiply(BigInteger.valueOf(-1));
    }

    @Override
    public BigInteger getValue(int value) {
        return BigInteger.valueOf(value);
    }
}
