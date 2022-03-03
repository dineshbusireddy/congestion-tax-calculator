package com.volvo.calculators;

import com.volvo.checkers.TaxExemptChecker;
import com.volvo.model.TaxableIntervalsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Calculates the tax for the given date
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
@Service
public class TaxCalculatorImpl implements TaxCalculator {

    @Autowired
    private TaxExemptChecker taxExemptChecker;

    @Autowired
    private TaxableIntervalsConfig taxableIntervalsConfig;

    @Override
    public BigDecimal calculateTax(LocalDateTime date) {
        BigDecimal tax = BigDecimal.ZERO;
        if (!taxExemptChecker.isTaxFreeDay(date)) {
            tax = taxableIntervalsConfig.getIntervals().stream().filter(interval ->
                    isEntryTimeFallUnderTaxIntervals(date, interval)).findFirst()
                    .orElse(new TaxableIntervalsConfig.TaxInterval()).getPrice();
        }

        return tax;
    }

    /**
     * Checks the provided dates falls under any tax rule intervals
     *
     * @param tollEntryDateAndTime the {@link LocalDateTime} of toll entry
     * @param taxInterval          the configured {@link com.volvo.model.TaxableIntervalsConfig.TaxInterval}
     * @return true if any tax rule matches else false
     */
    private boolean isEntryTimeFallUnderTaxIntervals(LocalDateTime tollEntryDateAndTime,
                                                     TaxableIntervalsConfig.TaxInterval taxInterval) {
        boolean isInBetween;
        LocalTime entryTime = LocalTime.of(tollEntryDateAndTime.getHour(), tollEntryDateAndTime.getMinute());
        if (taxInterval.getEndTime().isAfter(taxInterval.getStartTime())) {
            isInBetween = (taxInterval.getStartTime().isBefore(entryTime)
                    || taxInterval.getStartTime().equals(entryTime))
                    && (taxInterval.getEndTime().isAfter(entryTime)
                    || taxInterval.getEndTime().equals(entryTime));
        } else {
            isInBetween = entryTime.isAfter(taxInterval.getStartTime())
                    || entryTime.equals(taxInterval.getStartTime())
                    || entryTime.isBefore(taxInterval.getEndTime())
                    || entryTime.equals(taxInterval.getEndTime());
        }

        return isInBetween;
    }
}
