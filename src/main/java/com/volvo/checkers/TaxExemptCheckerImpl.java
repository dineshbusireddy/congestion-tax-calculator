package com.volvo.checkers;

import com.volvo.constants.Constants;
import com.volvo.model.HolidaysOfMonth;
import com.volvo.model.HolidaysOfYear;
import com.volvo.model.TaxExemptInfo;
import com.volvo.model.VehicleType;
import com.volvo.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * To check whether the provided vehicle or date tax free or not
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
@Service
public class TaxExemptCheckerImpl implements TaxExemptChecker {

    @Autowired
    private TaxExemptInfo taxExemptInfo;


    @Override
    public boolean isTaxFreeVehicle(VehicleType vehicleType) {
        return taxExemptInfo.getVehicleTypes().contains(vehicleType);
    }

    @Override
    public boolean isTaxFreeDay(LocalDateTime date) {
        return isTaxFreeWeekDay(date) || isHoliday(date);
    }

    /**
     * Checks the given date falls under weekends or any configured week date of tax exempt
     *
     * @param date the {@link LocalDateTime} of toll entry
     * @return true if matches rule else false
     */
    private boolean isTaxFreeWeekDay(LocalDateTime date) {
        return CollectionUtil.isNotEmpty(taxExemptInfo.getWeekDays())
                && taxExemptInfo.getWeekDays().contains(date.getDayOfWeek());
    }

    /**
     * Checks whether the given date falls under any holiday
     *
     * @param date the {@link LocalDateTime} of toll entry
     * @return true if matches rule else false
     */
    private boolean isHoliday(LocalDateTime date) {
        HolidaysOfYear holidaysOfYear = taxExemptInfo.getHolidaysByYear(date.getYear());

        return holidaysOfYear != null
                && (isInHolidaysOfMonth(date, holidaysOfYear)
                || isTheDayBeforeHoliday(date, holidaysOfYear));
    }

    /**
     * Checks whether the given date falls under any holiday
     *
     * @param date           the {@link LocalDateTime} of toll entry
     * @param holidaysOfYear the {@link HolidaysOfYear}
     * @return true if matches rule else false
     */
    private boolean isInHolidaysOfMonth(LocalDateTime date, HolidaysOfYear holidaysOfYear) {
        return isEntireMonthHoliday(date, holidaysOfYear)
                || isInHolidayList(date, holidaysOfYear.getHolidaysByMonth(date.getMonth()));
    }

    /**
     * Checks whether the entire month is Holiday or not
     *
     * @param date           the {@link LocalDateTime} of toll entry
     * @param holidaysOfYear the {@link HolidaysOfYear}
     * @return true if matches rule else false
     */
    private boolean isEntireMonthHoliday(
            LocalDateTime date, HolidaysOfYear holidaysOfYear) {
        return holidaysOfYear.containsMonth(date.getMonth())
                && CollectionUtil.
                isEmpty(holidaysOfYear.getHolidaysByMonth(date.getMonth()).getHolidays());
    }

    /**
     * Checks whether the provided the date in holiday list or not
     *
     * @param date            the {@link LocalDateTime} of toll entry
     * @param holidaysOfMonth the {@link HolidaysOfMonth}
     * @return true if matches rule else false
     */
    private boolean isInHolidayList(LocalDateTime date, HolidaysOfMonth holidaysOfMonth) {
        return holidaysOfMonth != null
                && holidaysOfMonth.isHoliday(date.getDayOfMonth());
    }

    /**
     * Checks whether the provided the date is before holiday or not
     *
     * @param date            the {@link LocalDateTime} of toll entry
     * @param holidaysOfMonth the {@link HolidaysOfMonth}
     * @return true if matches rule else false
     */
    private boolean isTheDayBeforeHoliday(LocalDateTime date, HolidaysOfYear holidaysOfYear) {
        LocalDateTime nextDay = date.plusDays(Constants.ONE);

        return isInHolidayList(nextDay, holidaysOfYear.getHolidaysByMonth(nextDay.getMonth()));
    }

}
