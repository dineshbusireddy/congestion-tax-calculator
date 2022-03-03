package com.volvo.model;

import com.volvo.util.CollectionUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.Month;
import java.util.List;

/**
 * Holds holidays of month which will read from application-XXX.yml file
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
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
