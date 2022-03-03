package com.volvo.util;

import org.springframework.lang.Nullable;

/**
 * Provides utility methods related for String
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
public class StringUtil {

    /**
     * Checks whether the given string not empty or not
     *
     * @param input the input string
     * @return true if not empty else false
     */
    public static boolean isNotEmpty(@Nullable CharSequence input) {
        return ObjectUtil.isNotNull(input) && input.length() > 0;
    }
}
