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

@Service
@Setter
public class TaxProcessor {

    @Value("${single-charge.time-in-minutes}")
    private int singleChargeTime;

    @Value("${maximum-tax-per-day-in-seks:0}")
    private BigDecimal maximumTaxPerDay;

    @Autowired
    private TaxCalculator taxCalculator;

    public BigDecimal calculateTaxForSingleDay(List<LocalDateTime> dates) {
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

    private boolean isWithInSingleChargeInterval(
            LocalDateTime startHourInterval,
            LocalDateTime currentTime) {
        LocalDateTime singleChargeMaxTime = startHourInterval.plusMinutes(singleChargeTime);
        return singleChargeMaxTime.isAfter(currentTime)
                || singleChargeMaxTime.equals(currentTime);
    }
}
