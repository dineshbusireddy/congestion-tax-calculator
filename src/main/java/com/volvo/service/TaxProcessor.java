package com.volvo.service;

import com.volvo.calculators.TaxCalculator;
import com.volvo.util.BigDecimalUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.volvo.constants.Constants.ZERO;
import static com.volvo.util.BigDecimalUtil.Operator.GREATER_THAN;
import static com.volvo.util.BigDecimalUtil.Operator.GREATER_THAN_OR_EQUALS;

/**
 * Processes or calculates the tax for the given dates of single day
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
@Service
@Setter
public class TaxProcessor {

    @Value("${single-charge.time-in-minutes}")
    private int singleChargeTime;

    @Value("${maximum-tax-per-day:1000}")
    private BigDecimal maximumTaxPerDay;

    @Autowired
    private TaxCalculator taxCalculator;

    /**
     * Calculates the tax for the given dates of single day
     * If multiple entries fall under single charge, max price will be considered
     * If the total tax per exceeds the configured maximum amount of the day,
     * the configured maximum amount will be considered
     *
     * @param dates the list {@link LocalDateTime} the vehicle passes in tolls
     * @return the calculated tax
     */
    public BigDecimal calculateTaxPerDay(List<LocalDateTime> dates) {
        BigDecimal totalTax = BigDecimal.ZERO;
        LocalDateTime singleChargeIntervalStartTime = dates.get(ZERO);
        BigDecimal singleChargeMaximumTax = taxCalculator.calculateTax(singleChargeIntervalStartTime);

        for (LocalDateTime entryTime : dates) {
            BigDecimal tax = taxCalculator.calculateTax(entryTime);

            if (isWithInSingleChargeInterval(singleChargeIntervalStartTime, entryTime)) {
                if (BigDecimalUtil.check(totalTax, GREATER_THAN, BigDecimal.ZERO)) {
                    totalTax = totalTax.subtract(singleChargeMaximumTax);
                }
                tax = singleChargeMaximumTax = tax.max(singleChargeMaximumTax);
            } else {
                singleChargeIntervalStartTime = entryTime;
                singleChargeMaximumTax = tax;
            }
            totalTax = totalTax.add(tax);

            if (BigDecimalUtil.check(totalTax, GREATER_THAN_OR_EQUALS, maximumTaxPerDay)) {
                totalTax = maximumTaxPerDay;
                break;
            }
        }

        return totalTax.min(maximumTaxPerDay);
    }

    /**
     * Chekes the given date falls under single charge or not
     *
     * @param startHourInterval the {@link LocalDateTime} start of the single charge interval
     * @param entryTime         the {@link LocalDateTime} toll entry time
     * @return true if rule matches else false
     */
    private boolean isWithInSingleChargeInterval(
            LocalDateTime startHourInterval,
            LocalDateTime entryTime) {
        LocalDateTime singleChargeMaxTime = startHourInterval.plusMinutes(singleChargeTime);

        return singleChargeMaxTime.isAfter(entryTime)
                || singleChargeMaxTime.equals(entryTime);
    }
}
