package com.volvo.checkers;

import com.volvo.model.HolidaysOfMonth;
import com.volvo.model.HolidaysOfYear;
import com.volvo.model.TaxExemptInfo;
import com.volvo.model.VehicleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.volvo.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaxExemptCheckerImplTest {

    @InjectMocks
    private TaxExemptCheckerImpl taxExemptChecker;

    @Mock
    private TaxExemptInfo taxExemptInfo;

    @BeforeEach
    void setUp() {
        when(taxExemptInfo.getWeekDays()).
                thenReturn(new HashSet<>(Arrays.asList(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY)));
        when(taxExemptInfo.getHolidaysByYear(anyInt())).
                thenReturn(getHolidaysOfYear());
    }

    @Test
    void testIsTaxFreeVehicle() {
        when(taxExemptInfo.getVehicleTypes()).
                thenReturn(new HashSet<>(Arrays.asList(VehicleType.EMERGENCY)));
        assertTrue(taxExemptChecker.isTaxFreeVehicle(VehicleType.EMERGENCY));
        assertFalse(taxExemptChecker.isTaxFreeVehicle(VehicleType.CAR));
    }

    @Test
    void testFreeOnWeekend() {
        assertTrue(taxExemptChecker.isTaxFreeDay(LocalDateTime.of(TEST_YEAR, Month.JANUARY, SIX, ZERO, ZERO)));
        assertTrue(taxExemptChecker.isTaxFreeDay(LocalDateTime.of(TEST_YEAR, Month.JANUARY, FIVE, ZERO, ZERO)));
        assertFalse(taxExemptChecker.isTaxFreeDay(LocalDateTime.of(TEST_YEAR, Month.JANUARY, TWO, ZERO, ZERO)));
    }

    @Test
    void testHoliday() {
        assertTrue(taxExemptChecker.isTaxFreeDay(LocalDateTime.of(TEST_YEAR, Month.MARCH, SIX, ZERO, ZERO)));
        assertTrue(taxExemptChecker.isTaxFreeDay(LocalDateTime.of(TEST_YEAR, Month.MARCH, ONE, ZERO, ZERO)));
    }

    @Test
    void testDayBeforeHoliday() {
        assertTrue(taxExemptChecker.isTaxFreeDay(
                LocalDateTime.of(TEST_YEAR, Month.MARCH, FIVE, ZERO, ZERO)));
        assertFalse(taxExemptChecker.isTaxFreeDay(
                LocalDateTime.of(TEST_YEAR, Month.MARCH, SEVEN, ZERO, ZERO)));
        assertTrue(taxExemptChecker.isTaxFreeDay(
                LocalDateTime.of(TEST_YEAR, Month.FEBRUARY, TWENTY_EIGHT, ZERO, ZERO)));
        assertFalse(taxExemptChecker.isTaxFreeDay(
                LocalDateTime.of(TEST_YEAR, Month.FEBRUARY, TWENTY_SEVEN, ZERO, ZERO)));
    }

    private HolidaysOfYear getHolidaysOfYear() {
        HolidaysOfYear holidaysOfYear = new HolidaysOfYear();
        holidaysOfYear.setYear(TEST_YEAR);
        holidaysOfYear.setMonths(getHolidaysOfMonth());

        return holidaysOfYear;
    }

    private List<HolidaysOfMonth> getHolidaysOfMonth() {
        HolidaysOfMonth holidaysOfMonth = new HolidaysOfMonth();
        holidaysOfMonth.setMonth(Month.MARCH);
        holidaysOfMonth.setHolidays(Arrays.asList(ONE, SIX));

        return Arrays.asList(holidaysOfMonth);
    }
}