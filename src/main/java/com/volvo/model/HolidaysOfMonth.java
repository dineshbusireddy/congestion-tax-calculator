package com.volvo.model;

import com.volvo.util.CollectionUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.Month;
import java.util.List;

@Getter
@Setter
public class HolidaysOfMonth {
    private Month month;
    private List<Integer> holidays;

    public boolean isHoliday(int dayOfMonth) {
        return CollectionUtil.isNotEmpty(holidays)
                && holidays.contains(dayOfMonth);
    }
}
