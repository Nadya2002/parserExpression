package expression.parser;

import expression.*;

import java.util.ArrayList;
import java.util.List;

public class MyExpressionParser<T> extends BaseParser implements MyParser<T> {
    private String expression;
    private List<String> elements = new ArrayList<>();
    private List<Type> type = new ArrayList<>();
    private List<MyTripleExpression<T>> myExpression = new ArrayList<>();
    private int currentIndex = 0;
    private int currentPositionOnList = 0;

    public MyExpressionParser() {
        expression = "";
    }

    protected void nextChar() {
        if (currentIndex < expression.length()) {
            ch = expression.charAt(currentIndex);
            currentIndex++;
        } else {
            ch = END;
        }
    }

    public boolean test(char expected) {
        if (ch == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    public MyTripleExpression<T> parse(final String source) {
        elements = new ArrayList<>();
        type = new ArrayList<>();
        myExpression = new ArrayList<>();
        currentIndex = 0;
        currentPositionOnList = 0;
        expression = source;
        ch = expression.charAt(0);
        elements = splitOfPart(expression);

        if (correctExpr(type)) {
            final MyTripleExpression<T> result = expr(elements);
            return result;
        } else {
            throw new ParseException("incorrect expression: " + expression);
        }
    }

    private boolean correctExpr(List<Type> type) {
        if (type.size() == 2) {
            if (type.get(0) == Type.VAR || type.get(0) == Type.NUM) {
                return true;
            } else {
                return false;
            }
        }

        int balance = 0;

        for (int i = 0; i < elements.size(); i++) {
            switch (type.get(i)) {
                case OPEN_BRACKET:
                    if (i > 0 && (type.get(i - 1) == Type.VAR || type.get(i - 1) == Type.NUM || type.get(i - 1) == Type.CLOSE_BRACKET) ||
                            (i == 1 && (type.get(0) != Type.OPEN_BRACKET && type.get(0) != Type.MINUS && type.get(0) != Type.FUNC))) {
                        throw new ParseException("missing argument" + printError(elements, i - 1));
                    } else {
                        balance++;
                        break;
                    }
                case CLOSE_BRACKET:
                    balance--;
                    if (balance < 0) {
                        throw new ParseException("extra close bracket " + printErrorUnknown(elements, i));
                    } else if (i == 0) {
                        throw new ParseException(printError(elements, i - 1));
                    } else if ((type.get(i - 1) != Type.VAR && type.get(i - 1) != Type.NUM && type.get(i - 1) != Type.CLOSE_BRACKET)) {
                        throw new ParseException("missing argument" + printError(elements, i));
                    } else {
                        break;
                    }
                case NUM:
                case VAR:
                    if (i > 0 && ((type.get(i - 1) == Type.VAR || type.get(i - 1) == Type.NUM || type.get(i - 1) == Type.CLOSE_BRACKET)
                            || i == 1 && (type.get(0) != Type.OPEN_BRACKET && type.get(0) != Type.MINUS && type.get(0) != Type.FUNC))) {
                        throw new ParseException("missing operator" + printError(elements, i));
                    } else {
                        break;
                    }
                case FUNC:
                    if (i > 0 && ((type.get(i - 1) == Type.VAR || type.get(i - 1) == Type.NUM || type.get(i - 1) == Type.CLOSE_BRACKET)
                            || i == 1 && (type.get(0) != Type.OPEN_BRACKET && type.get(0) != Type.MINUS && type.get(0) != Type.FUNC))) {
                        throw new ParseException("missing argument" + printError(elements, i));
                    } else if (type.get(i + 1) != Type.VAR && type.get(i + 1) != Type.NUM && type.get(i + 1) != Type.OPEN_BRACKET && type.get(i + 1) != Type.MINUS && type.get(i + 1) != Type.FUNC) {
                        throw new ParseException("incorrect argument" + printErrorUnknown(elements, i + 1));
                    } else {
                        break;
                    }
                case MINUS:
                    if (i > 0 && type.get(i - 1) != Type.VAR && type.get(i - 1) != Type.NUM && type.get(i - 1) != Type.CLOSE_BRACKET && type.get(i - 1) != Type.OPEN_BRACKET && type.get(i - 1) != Type.FUNC &&
                            type.get(i - 1) != Type.MINUS && type.get(i + 1) != Type.MINUS && type.get(i + 1) != Type.VAR && type.get(i + 1) != Type.NUM && type.get(i + 1) != Type.OPEN_BRACKET && type.get(i + 1) != Type.FUNC) {
                        throw new ParseException("missing argument" + printError(elements, i - 1));
                    } else {
                        break;
                    }
                case UNKNOWN:
                    throw new ParseException("unknown symbol in expression" + printErrorUnknown(elements, i));
                default:
                    if (i == 0) {
                        throw new ParseException("missing argument" + printError(elements, i - 1));
                    } else if (type.get(i - 1) != Type.VAR && type.get(i - 1) != Type.NUM && type.get(i - 1) != Type.CLOSE_BRACKET || i == type.size() - 2) {
                        if (i == type.size() - 2) {
                            throw new ParseException("missing argument" + printError(elements, i + 1));
                        }
                        throw new ParseException("missing argument" + printError(elements, i));
                    } else {
                        break;
                    }
            }
        }

        if (balance != 0) {
            throw new ParseException("not close bracket" + printError(elements, elements.size()));
        }
        return true;
    }

    private String printErrorUnknown(List<String> elements, int pos) {
        StringBuilder error = new StringBuilder("\n\t\t\t\t\t\t\t\t");
        StringBuilder location = new StringBuilder("\t\t\t\t\t\t\t\t");
        if (pos == -1) {
            location.append("^ ");
            for (int i = 0; i < elements.size(); i++) {
                error.append(elements.get(i));
                error.append(" ");
            }
        } else {
            for (int i = 0; i < pos; i++) {
                error.append(elements.get(i));
                error.append(" ");
                addWhitespace(location, elements.get(i));
            }
            location.append("^ ");
            for (int i = pos; i < elements.size(); i++) {
                error.append(elements.get(i));
                error.append(" ");
            }
        }
        error.append("\n");
        error.append(location);
        return error.toString();
    }

    private void addWhitespace(StringBuilder location, String variable) {
        if (variable.length() > 1) {
            for (int i = 0; i < variable.length(); i++) {
                location.append(" ");
            }
            location.append(" ");
        } else {
            location.append("  ");
        }
    }

    private String printError(List<String> elements, int pos) {
        StringBuilder error = new StringBuilder("\n\t\t\t\t\t\t\t\t");
        StringBuilder location = new StringBuilder("\t\t\t\t\t\t\t\t");

        if (pos == -1) {
            error.append(" ");
            location.append("^ ");
            for (int i = 0; i < elements.size(); i++) {
                error.append(elements.get(i));
                error.append(" ");
            }
        } else {
            for (int i = 0; i < pos; i++) {
                error.append(elements.get(i));
                error.append(" ");
                addWhitespace(location, elements.get(i));
            }
            error.append("  ");
            location.append("^ ");
            for (int i = pos; i < elements.size(); i++) {
                error.append(elements.get(i));
                error.append(" ");
            }
        }
        error.append("\n");
        error.append(location);

        return error.toString();
    }

    private List<String> splitOfPart(String expression) {

        while (currentIndex < expression.length()) {

            skipWhitespace();

            if (Character.isLetter(ch)) {
                String var = partVariable();
                if (var.equals("x") || var.equals("y") || var.equals("z")) {
                    elements.add(var);
                    type.add(Type.VAR);
                    currentIndex--;
                } else if (var.equals("abs") || var.equals("sqrt")) {
                    elements.add(var);
                    type.add(Type.FUNC);
                    currentIndex--;
                } else {
                    elements.add(var);
                    type.add(Type.UNKNOWN);
                    currentIndex--;
                }
            } else if (isDigit(ch)) {
                elements.add(partConst());
                type.add(Type.NUM);
                currentIndex--;
            } else {
                if (ch == '(') {
                    type.add(Type.OPEN_BRACKET);
                } else if (ch == ')') {
                    type.add(Type.CLOSE_BRACKET);
                } else if (ch == '+') {
                    type.add(Type.PLUS);
                } else if (ch == '*') {
                    type.add(Type.MUL);
                } else if (ch == '/') {
                    type.add(Type.DIV);
                } else if (ch == '-') {
                    type.add(Type.MINUS);
                } else if (ch == '&') {
                    type.add(Type.AND);
                } else if (ch == '^') {
                    type.add(Type.XOR);
                } else if (ch == '|') {
                    type.add(Type.OR);
                } else {
                    type.add(Type.UNKNOWN);
                }

                elements.add(String.valueOf(ch));
                currentIndex++;
                if (currentIndex < expression.length()) {
                    ch = expression.charAt(currentIndex);
                }
            }
        }

        if (type.get(type.size() - 1) == Type.UNKNOWN && (int) elements.get(elements.size() - 1).charAt(0) == 0) {
            elements.remove(elements.size() - 1);
            type.remove(type.size() - 1);
        }

        type.add(Type.END);
        return elements;
    }

    private MyTripleExpression<T> expr(List<String> elements) {
        currentPositionOnList--;

        MyTripleExpression<T> elem = plusMinus(elements);

        myExpression.add(elem);

        return myExpression.get(myExpression.size() - 1);
    }


    private MyTripleExpression<T> plusMinus(List<String> elements) {
        MyTripleExpression<T> first = mulDiv(elements);

        while (true) {
            currentPositionOnList++;
            if (type.get(currentPositionOnList) == Type.PLUS || type.get(currentPositionOnList) == Type.MINUS) {
                Type action = type.get(currentPositionOnList);
                MyTripleExpression<T> second = mulDiv(elements);
                MyExpression<T> one, two;
                one = (MyExpression) first;
                two = (MyExpression) second;

                if (action == Type.PLUS) {
                    myExpression.add(new Add<>(one, two));
                } else {
                    myExpression.add(new Subtract<>(one, two));
                }

                first = myExpression.get(myExpression.size() - 1);
            } else {
                currentPositionOnList--;
                return myExpression.get(myExpression.size() - 1);
            }
        }
    }

    private MyTripleExpression<T> mulDiv(List<String> elements) {
        MyTripleExpression<T> first = factor(elements);

        while (true) {
            currentPositionOnList++;
            if (type.get(currentPositionOnList) == Type.MUL || type.get(currentPositionOnList) == Type.DIV) {
                Type action = type.get(currentPositionOnList);
                MyTripleExpression<T> second = factor(elements);
                MyExpression<T> one, two;
                one = (MyExpression) first;
                two = (MyExpression) second;

                if (action == Type.MUL) {
                    myExpression.add(new Multiply<>(one, two));
                } else {
                    myExpression.add(new Divide<>(one, two));
                }

                first = myExpression.get(myExpression.size() - 1);
            } else {
                currentPositionOnList--;
                return myExpression.get(myExpression.size() - 1);
            }
        }
    }


    private MyTripleExpression<T> unarnOperation(List<String> elements) {
        MyTripleExpression<T> first = factor(elements);
        MyExpression<T> one;
        one = (MyExpression) first;
        if (one instanceof Const) {
            int number = (((Const) one).getValue()) * (-1);
            myExpression.add(new Const(number));
        } else {
            myExpression.add(new Minus<>(one));
        }

        return myExpression.get(myExpression.size() - 1);
    }

    private MyTripleExpression<T> factor(List<String> elements) {
        currentPositionOnList++;
        String element = elements.get(currentPositionOnList);
        if (type.get(currentPositionOnList) == Type.NUM) {
            if (currentPositionOnList > 0 && type.get(currentPositionOnList - 1) == Type.MINUS) {
                element = "-" + element;
                myExpression.add(new Minus((MyExpression) parseConst(element)));
            } else {
                myExpression.add(parseConst(element));
            }
        } else if (type.get(currentPositionOnList) == Type.VAR) {
            myExpression.add(parseVariable(element));
        } else if (type.get(currentPositionOnList) == Type.OPEN_BRACKET) {
            myExpression.add(plusMinus(elements));
            currentPositionOnList++;
        } else if (type.get(currentPositionOnList) == Type.MINUS) {
            myExpression.add(unarnOperation(elements));
        }
        return myExpression.get(myExpression.size() - 1);
    }

    private String partConst() {
        currentIndex++;
        final StringBuilder number = new StringBuilder();
        copyInteger(number);
        return number.toString();
    }

    private String partVariable() {
        StringBuilder variable = new StringBuilder();
        currentIndex++;
        while (Character.isLetter(ch)) {
            variable.append(ch);
            nextChar();
        }
        return variable.toString();
    }

    private MyTripleExpression<T> parseConst(String number) {
        try {
            return new Const(Integer.parseInt(number));
        } catch (NumberFormatException e) {
            throw new ParseException("overflow when parsing number " + number);
        }
    }

    private MyTripleExpression<T> parseVariable(String variable) {
        return new Variable<T>(variable);
    }

    private void copyDigits(final StringBuilder sb) {
        while (between('0', '9')) {
            sb.append(ch);
            nextChar();
        }
    }

    private void copyInteger(final StringBuilder sb) {
        if (test('-')) {
            sb.append('-');
        }
        if (test('0')) {
            sb.append('0');
        } else if (between('1', '9')) {
            copyDigits(sb);
        } else {
            throw error("Invalid number");
        }
    }

    private boolean isDigit(char ch) {
        if ('0' <= ch && ch <= '9') {
            return true;
        } else {
            return false;
        }
    }

    private boolean isOperator(char ch) {
        if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
            return true;
        } else {
            return false;
        }
    }

    public void skipWhitespace() {
        boolean skip = false;
        while (test(' ') || test('\r') || test('\n') || test('\t')) {
            skip = true;
            // skip
        }
        if (skip) {
            currentIndex--;
        }
    }

    enum Type {
        OPEN_BRACKET, CLOSE_BRACKET,
        PLUS, MINUS,
        MUL, DIV,
        NUM, VAR,
        AND, OR, XOR,
        FUNC,
        UNKNOWN,
        END
    }
}
