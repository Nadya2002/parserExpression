package expression.generic;

import expression.*;
import expression.parser.MyExpressionParser;

public class GenericTabulator<T> implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return createTable(choiceType(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] createTable(GenericOperation<T> operation, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        MyExpressionParser<T> parser = new MyExpressionParser<>();
        MyTripleExpression<T> expr = parser.parse(expression);

        Object[][][] table = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = 0; i <= x2 - x1; i++) {
            for (int j = 0; j <= y2 - y1; j++) {
                for (int k = 0; k <= z2 - z1; k++) {
                    try {
                        table[i][j][k] = expr.evaluate(operation.getValue(x1 + i), operation.getValue(y1 + j), operation.getValue(z1 + k), operation);
                    } catch (Exception e) {
                        table[i][j][k] = null;
                    }
                }
            }
        }

        return table;
    }

    private GenericOperation<?> choiceType(String mode) {
        switch (mode) {
            case "i":
                return new GenericIntegerCheck();
            case "d":
                return new GenericDouble();
            case "bi":
                return new GenericBigInt();
            case "u":
                return new GenericInteger();
            case "l":
                return new GenericLong();
            default:    //s
                return new GenericShort();
        }
    }
}
