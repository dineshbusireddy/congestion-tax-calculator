package com.volvo.util;

import java.math.BigDecimal;

import static com.volvo.constants.Constants.ZERO;

/**
 * Provides utility methods related to {@link BigDecimal}
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
public class BigDecimalUtil {

    /**
     * Compares two {@link BigDecimal} values by provided operator
     *
     * @param firstNum  the {@link BigDecimal}
     * @param operator  the {@link Operator}
     * @param secondNum the {@link BigDecimal}
     * @return true if satisfy the provided operator else false
     */
    public static boolean check(BigDecimal firstNum, Operator operator, BigDecimal secondNum) {
        if (ObjectUtil.isNull(firstNum) || ObjectUtil.isNull(secondNum)) {
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
