package com.volvo.model;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds holidays details which will read from application-XXX.yml file
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
@Configuration
@ConfigurationProperties(prefix = "holidays")
@Getter
public class HolidaysInfo {

    private Map<Integer, HolidaysOfYear> years;

    public void setYears(List<HolidaysOfYear> holidaysOfYears) {
        this.years = new HashMap<>();
        for (HolidaysOfYear holidaysOfYear : holidaysOfYears) {
            this.years.put(holidaysOfYear.getYear(), holidaysOfYear);
        }
    }

    public HolidaysOfYear getHolidaysByYear(int year) {
        return this.years.get(year);
    }


}
