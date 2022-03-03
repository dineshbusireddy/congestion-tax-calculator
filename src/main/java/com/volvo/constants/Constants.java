package com.volvo.constants;

import java.time.format.DateTimeFormatter;

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
