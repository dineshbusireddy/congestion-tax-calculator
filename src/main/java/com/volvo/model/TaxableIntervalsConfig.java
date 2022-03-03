package com.volvo.model;

import com.volvo.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import static com.volvo.constants.Constants.HYPHEN;
import static com.volvo.constants.Constants.ONE;
import static com.volvo.constants.Constants.TIME_FORMATTER;
import static com.volvo.constants.Constants.TWO;
import static com.volvo.constants.Constants.ZERO;

/**
 * Holds taxable intervals and pricing which will read from application-XXX.yml file
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
@Configuration
@ConfigurationProperties(prefix = "tax-intervals")
@Getter
@Setter
public class TaxableIntervalsConfig {
    private List<TaxInterval> intervals;

    @Getter
    @Setter
    public static class TaxInterval {
        private LocalTime startTime;
        private LocalTime endTime;
        private BigDecimal price;

        public void setInterval(String interval) {
            if (StringUtil.isNotEmpty(interval)
                    && interval.split(HYPHEN).length == TWO) {
                String[] intervals = interval.split(HYPHEN);
                startTime = LocalTime.parse(intervals[ZERO].trim(), TIME_FORMATTER);
                endTime = LocalTime.parse(intervals[ONE].trim(), TIME_FORMATTER);
            } else {
                throw new RuntimeException("Invalid interval in application.yml");
            }
        }

    }
}