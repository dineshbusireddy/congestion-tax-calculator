package com.volvo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * Holds tax free dates and vehicle info which will read from application-XXX.yml file
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
@Configuration
@ConfigurationProperties(prefix = "tax-exempt")
@Getter
@Setter
public class TaxExemptInfo {
    private Set<DayOfWeek> weekDays;
    private Set<VehicleType> vehicleTypes;
    private HolidaysInfo holidaysInfo;

    public HolidaysOfYear getHolidaysByYear(int year) {
        return holidaysInfo.getHolidaysByYear(year);
    }

}
