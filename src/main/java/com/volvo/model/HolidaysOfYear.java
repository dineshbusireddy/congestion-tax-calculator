package com.volvo.model;

import lombok.Getter;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class HolidaysOfYear {

    private int year;
    private Map<Month, HolidaysOfMonth> months;

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonths(List<HolidaysOfMonth> holidaysOfMonths) {
        this.months = new HashMap<>();
        for (HolidaysOfMonth holidaysOfMonth : holidaysOfMonths) {
            this.months.put(holidaysOfMonth.getMonth(), holidaysOfMonth);
        }
    }

    public HolidaysOfMonth getHolidaysByMonth(Month monthOfYear) {
        return this.months.get(monthOfYear);
    }

    public boolean containsMonth(Month monthOfYear) {
        return this.months.containsKey(monthOfYear);
    }
}

