package com.volvo.calculators;

import com.volvo.checkers.TaxExemptChecker;
import com.volvo.model.TaxableIntervalsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static com.volvo.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaxCalculatorImplTest {

    @InjectMocks
    private TaxCalculatorImpl taxCalculator;

    @Mock
    private TaxExemptChecker taxExemptChecker;

    @Mock
    private TaxableIntervalsConfig taxableIntervalsConfig;

    @BeforeEach
    void setUp() {
        when(taxExemptChecker.isTaxFreeDay(any(LocalDateTime.class))).thenReturn(false);
    }

    @Test
    void calculateTaxWithTaxFreeDay() {
        when(taxExemptChecker.isTaxFreeDay(any(LocalDateTime.class))).thenReturn(true);
        assertEquals(BigDecimal.ZERO, taxCalculator.calculateTax(LocalDateTime.now()));
    }

    @Test
    void calculateTaxWithEndIntervalGreaterThanStartInterval() {
        when(taxableIntervalsConfig.getIntervals()).
                thenReturn(getIntervals(TIME_INTERVAL_END_GREATER_THAN_START, BigDecimal.ONE));
        assertEquals(BigDecimal.ONE, taxCalculator.calculateTax(
                LocalDateTime.of(TEST_YEAR, Month.JANUARY, TWO, SIX, FORTY_FIVE)));
    }

    @Test
    void calculateTaxWithEndIntervalLesserThanStartInterval() {
        when(taxableIntervalsConfig.getIntervals()).
                thenReturn(getIntervals(TIME_INTERVAL_START_GREATER_THAN_END, BigDecimal.ZERO));
        assertEquals(BigDecimal.ZERO, taxCalculator.calculateTax(
                LocalDateTime.of(TEST_YEAR, Month.JANUARY, TWO, TEN, FORTY_FIVE)));
        assertEquals(BigDecimal.ZERO, taxCalculator.calculateTax(
                LocalDateTime.of(TEST_YEAR, Month.JANUARY, TWO, ZERO, FORTY_FIVE)));
    }

    @Test
    void calculateTaxWithStartOrEndIntervalEqualToEntryTime() {
        when(taxableIntervalsConfig.getIntervals()).
                thenReturn(getIntervals(TIME_INTERVAL_END_GREATER_THAN_START, BigDecimal.TEN));
        assertEquals(BigDecimal.TEN, taxCalculator.calculateTax(
                LocalDateTime.of(TEST_YEAR, Month.JANUARY, TWO, SIX, THIRTY)));
        assertEquals(BigDecimal.TEN, taxCalculator.calculateTax(
                LocalDateTime.of(TEST_YEAR, Month.JANUARY, TWO, SEVEN, FORTY_FIVE)));
    }

    @Test
    void calculateTaxWithEndIntervalLesserThanStartIntervalAndEqualToEntryTime() {
        when(taxableIntervalsConfig.getIntervals()).
                thenReturn(getIntervals(TIME_INTERVAL_START_GREATER_THAN_END, BigDecimal.ZERO));
        assertEquals(BigDecimal.ZERO, taxCalculator.calculateTax(
                LocalDateTime.of(TEST_YEAR, Month.JANUARY, TWO, NINE, THIRTY)));
        assertEquals(BigDecimal.ZERO, taxCalculator.calculateTax(
                LocalDateTime.of(TEST_YEAR, Month.JANUARY, TWO, TWO, FORTY_FIVE)));
    }

    private List<TaxableIntervalsConfig.TaxInterval> getIntervals(
            String interval,
            BigDecimal price
    ) {
        TaxableIntervalsConfig.TaxInterval taxInterval = new TaxableIntervalsConfig.TaxInterval();
        taxInterval.setInterval(interval);
        taxInterval.setPrice(price);
        return Arrays.asList(taxInterval);
    }
}