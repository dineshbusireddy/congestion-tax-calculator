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

    private boolean isTaxFreeWeekDay(LocalDateTime date) {
        return CollectionUtil.isNotEmpty(taxExemptInfo.getWeekDays())
                && taxExemptInfo.getWeekDays().contains(date.getDayOfWeek());
    }

    private boolean isHoliday(LocalDateTime date) {
        HolidaysOfYear holidaysOfYear = taxExemptInfo.getHolidaysByYear(date.getYear());
        return holidaysOfYear != null
                && (isInHolidaysOfMonth(date, holidaysOfYear)
                || isTheDayBeforeHoliday(date, holidaysOfYear));
    }

    private boolean isInHolidaysOfMonth(LocalDateTime date, HolidaysOfYear holidaysOfYear) {
        return isEntireMonthHoliday(date, holidaysOfYear)
                || isInHolidayList(date, holidaysOfYear.getHolidaysByMonth(date.getMonth()));
    }

    private boolean isEntireMonthHoliday(
            LocalDateTime date, HolidaysOfYear holidaysOfYear) {
        return holidaysOfYear.containsMonth(date.getMonth())
                && holidaysOfYear.getHolidaysByMonth(date.getMonth()) == null;
    }


    private boolean isInHolidayList(LocalDateTime date, HolidaysOfMonth holidaysOfMonth) {
        return holidaysOfMonth != null
                && holidaysOfMonth.isHoliday(date.getDayOfMonth());
    }

    private boolean isTheDayBeforeHoliday(LocalDateTime date, HolidaysOfYear holidaysOfYear) {
        LocalDateTime nextDay = date.plusDays(Constants.ONE);
        return isInHolidayList(nextDay, holidaysOfYear.getHolidaysByMonth(nextDay.getMonth()));
    }

}
