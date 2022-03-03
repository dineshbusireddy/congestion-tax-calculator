package com.volvo.util;

/**
 * Provides utility methods related for Object
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
public class ObjectUtil {

    /**
     * Checks whether the given object null or not
     *
     * @param input the input object
     * @return true if null else false
     */
    public static boolean isNull(Object input) {
        return input == null;
    }

    /**
     * Checks whether the given object not null or not
     *
     * @param input the input object
     * @return true if not null else false
     */
    public static boolean isNotNull(Object input) {
        return !isNull(input);
    }
}
