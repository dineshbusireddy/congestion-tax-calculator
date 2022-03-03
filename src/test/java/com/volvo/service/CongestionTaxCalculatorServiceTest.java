package com.volvo.service;

import com.volvo.checkers.TaxExemptChecker;
import com.volvo.constants.Constants;
import com.volvo.constants.TestConstants;
import com.volvo.model.TaxInfo;
import com.volvo.model.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
class CongestionTaxCalculatorServiceTest {

    @InjectMocks
    private CongestionTaxCalculatorService congestionTaxCalculatorService;

    @Mock
    private TaxExemptChecker taxExemptChecker;

    @Mock
    private TaxProcessor taxProcessor;

    @BeforeEach
    void setUp() {
        when(taxExemptChecker.isTaxFreeVehicle(VehicleType.EMERGENCY)).thenReturn(true);
        when(taxExemptChecker.isTaxFreeVehicle(VehicleType.CAR)).thenReturn(false);
        when(taxProcessor.
                calculateTaxPerDay(anyList())).thenReturn(BigDecimal.TEN);
    }

    @Test
    void calculateTaxTaxFreeVehicle() {
        TaxInfo taxInfo = congestionTaxCalculatorService.
                calculateTax(VehicleType.EMERGENCY, Arrays.asList(LocalDateTime.now()));
        assertEquals(BigDecimal.ZERO, taxInfo.getTax());
    }

    @Test
    void calculateTaxForSingleDate() {
        TaxInfo taxInfo = congestionTaxCalculatorService.
                calculateTax(VehicleType.CAR, createDates("2013-01-07 06:15:00"));
        assertEquals(BigDecimal.TEN, taxInfo.getTax());
    }

    @Test
    void calculateTaxForMultipleDays() {
        TaxInfo taxInfo = congestionTaxCalculatorService.
                calculateTax(VehicleType.CAR, createDates("2013-01-07 06:15:00",
                        "2013-01-08 06:15:00",
                        "2013-01-09 06:15:00"));
        assertEquals(BigDecimal.valueOf(TestConstants.THIRTY), taxInfo.getTax());
    }

    private List<LocalDateTime> createDates(String... dates) {
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        for (String date : dates) {
            localDateTimes.add(LocalDateTime.parse(date, Constants.DATE_TIME_FORMAT_PATTERN));
        }

        return localDateTimes;
    }
}