package com.volvo.service;

import com.volvo.calculators.TaxCalculator;
import com.volvo.constants.Constants;
import com.volvo.constants.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaxProcessorTest {

    @InjectMocks
    private TaxProcessor taxProcessor;

    @Mock
    private TaxCalculator taxCalculator;

    @BeforeEach
    void setUp() {
        taxProcessor.setMaximumTaxPerDay(BigDecimal.valueOf(TestConstants.THIRTY));
        taxProcessor.setSingleChargeTime(TestConstants.FORTY_FIVE);
    }

    @Test
    void testWithSingleDate() {
        when(taxCalculator.
                calculateTax(any(LocalDateTime.class))).thenReturn(BigDecimal.valueOf(TestConstants.FIVE));
        assertEquals(BigDecimal.valueOf(TestConstants.FIVE), taxProcessor.calculateTaxForSingleDay(createDates("2013-01-07 06:00:00")));
    }

    @Test
    void testTaxInSingleChargeIntervalWithSamePrice() {
        when(taxCalculator.
                calculateTax(any(LocalDateTime.class))).thenReturn(BigDecimal.valueOf(TestConstants.FIVE));
        assertEquals(BigDecimal.valueOf(TestConstants.FIVE),
                taxProcessor.calculateTaxForSingleDay(createDates("2013-01-07 06:00:00", "2013-01-07 06:30:00", "2013-01-07 06:45:00")));
    }

    @Test
    void testTaxInSingleChargeIntervalWithDifferentPrice() {
        when(taxCalculator.
                calculateTax(stringToDate("2013-01-07 06:00:00"))).thenReturn(BigDecimal.valueOf(TestConstants.FIVE));
        when(taxCalculator.
                calculateTax(stringToDate("2013-01-07 06:45:00"))).thenReturn(BigDecimal.valueOf(TestConstants.SEVEN));
        assertEquals(BigDecimal.valueOf(TestConstants.SEVEN),
                taxProcessor.calculateTaxForSingleDay(createDates("2013-01-07 06:00:00", "2013-01-07 06:45:00")));
    }

    @Test
    void testTaxInBeyondSingleChargeIntervalWithSamePrice() {
        when(taxCalculator.
                calculateTax(any(LocalDateTime.class))).thenReturn(BigDecimal.valueOf(TestConstants.FIVE));
        assertEquals(BigDecimal.TEN,
                taxProcessor.calculateTaxForSingleDay(createDates("2013-01-07 06:00:00",
                        "2013-01-07 06:30:00",
                        "2013-01-07 06:45:00",
                        "2013-01-07 06:45:01")));
    }

    @Test
    void testMaximumTaxPerDay() {
        when(taxCalculator.
                calculateTax(any(LocalDateTime.class))).thenReturn(BigDecimal.TEN);
        assertEquals(BigDecimal.valueOf(TestConstants.THIRTY),
                taxProcessor.calculateTaxForSingleDay(createDates(
                        "2013-01-07 06:00:00",
                        "2013-01-07 08:00:00",
                        "2013-01-07 10:00:00",
                        "2013-01-07 12:00:00",
                        "2013-01-07 14:00:00")));
    }

    private List<LocalDateTime> createDates(String... dates) {
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        for (String date : dates) {
            localDateTimes.add(stringToDate(date));
        }

        return localDateTimes;
    }

    private LocalDateTime stringToDate(String date) {
        return LocalDateTime.parse(date, Constants.DATE_TIME_FORMAT_PATTERN);
    }
}