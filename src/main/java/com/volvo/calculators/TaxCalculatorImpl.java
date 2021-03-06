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
    private boolean isEntryTimeFallUnderTaxIntervals(
            LocalDateTime tollEntryDateAndTime,
            TaxableIntervalsConfig.TaxInterval taxInterval
    ) {
        boolean isInTaxableRange;
        LocalTime entryTime = LocalTime.of(tollEntryDateAndTime.getHour(),
                tollEntryDateAndTime.getMinute());
        if (!isEntryTimeFallUnderTwoDates(taxInterval)) {
            isInTaxableRange = isEntryTimeEqualToStartOrEndInterval(entryTime, taxInterval)
                    || isEntryTimeAfterStartOrBeforeEndInterval(entryTime, taxInterval);
        } else {
            isInTaxableRange = isEntryInTheRangeOfIntervalOrEqualToStartOrEndInterval(entryTime, taxInterval);
        }

        return isInTaxableRange;
    }

    private boolean isEntryInTheRangeOfIntervalOrEqualToStartOrEndInterval(
            LocalTime entryTime,
            TaxableIntervalsConfig.TaxInterval taxInterval
    ) {
        return isEntryInTheRangeOfStartAndEndInterval(entryTime, taxInterval)
                || isEntryTimeEqualToStartOrEndInterval(entryTime, taxInterval);
    }

    private boolean isEntryTimeEqualToStartOrEndInterval(
            LocalTime entryTime,
            TaxableIntervalsConfig.TaxInterval taxInterval
    ) {
        return taxInterval.getStartTime().equals(entryTime)
                || taxInterval.getEndTime().equals(entryTime);
    }

    private boolean isEntryInTheRangeOfStartAndEndInterval(
            LocalTime entryTime,
            TaxableIntervalsConfig.TaxInterval taxInterval
    ) {
        return taxInterval.getStartTime().isBefore(entryTime)
                && taxInterval.getEndTime().isAfter(entryTime);
    }

    private boolean isEntryTimeFallUnderTwoDates(TaxableIntervalsConfig.TaxInterval taxInterval) {
        return taxInterval.getEndTime().isAfter(taxInterval.getStartTime());
    }

    private boolean isEntryTimeAfterStartOrBeforeEndInterval(
            LocalTime entryTime,
            TaxableIntervalsConfig.TaxInterval taxInterval
    ) {
        return entryTime.isAfter(taxInterval.getStartTime())
                || entryTime.isBefore(taxInterval.getEndTime());
    }

}
