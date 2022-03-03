package com.volvo.constants;

import java.time.format.DateTimeFormatter;

/**
 * To hold all the constants
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
public interface Constants {
    DateTimeFormatter DATE_TIME_FORMAT_PATTERN = DateTimeFormatter.
            ofPattern("yyyy-MM-dd[' ']['T']HH:mm[:ss][.SSS][XXX]");
    DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    String HYPHEN = "-";
    String SPACE = " ";
    int ZERO = 0;
    int ONE = 1;
    int TWO = 2;
}
