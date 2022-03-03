package com.volvo.util;

import static com.volvo.constants.Constants.ZERO;

import java.math.BigDecimal;

public class BigDecimalUtil {
    public static boolean check(BigDecimal firstNum, Operator operator, BigDecimal secondNum) {
        if (firstNum == null || secondNum == null) {
            return false;
        }
        int value = firstNum.compareTo(secondNum);
        switch (operator) {
            case EQUALS:
                return value == ZERO;
            case LESS_THAN:
                return value < ZERO;
            case LESS_THAN_OR_EQUALS:
                return value <= ZERO;
            case GREATER_THAN:
                return value > ZERO;
            case GREATER_THAN_OR_EQUALS:
                return value >= ZERO;
        }

        throw new IllegalArgumentException("Invalid operator");
    }

    public static enum Operator {
        LESS_THAN, LESS_THAN_OR_EQUALS, GREATER_THAN, GREATER_THAN_OR_EQUALS, EQUALS
    }
}
