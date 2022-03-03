package com.volvo.calculators;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Calculates the tax for the given date
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
public interface TaxCalculator {
    /**
     * Calculates the tax for the provided date
     *
     * @param date {@link LocalDateTime}
     * @return the calculated tax
     */
    BigDecimal calculateTax(LocalDateTime date);
}
