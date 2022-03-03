package com.volvo.util;

import org.springframework.lang.Nullable;

public class StringUtil {

    public static boolean isNotEmpty(@Nullable CharSequence str) {
        return str != null && str.length() > 0;
    }
}
