package com.volvo.util;

import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Map;

/**
 * Provides utility methods related for collections
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
public class CollectionUtil {

    /**
     * Checks whether the given collection empty or not
     *
     * @param input the input collection
     * @return true if empty else false
     */
    public static boolean isEmpty(@Nullable Collection<?> input) {
        return ObjectUtil.isNull(input) || input.isEmpty();
    }

    /**
     * Checks whether the given collection not empty or not
     *
     * @param input the input collection
     * @return true if not empty else false
     */
    public static boolean isNotEmpty(@Nullable Collection<?> input) {
        return !isEmpty(input);
    }

    /**
     * Checks whether the given map empty or not
     *
     * @param input the input map
     * @return true if empty else false
     */
    public static boolean isEmpty(@Nullable Map<?, ?> input) {
        return ObjectUtil.isNull(input) || input.isEmpty();
    }

    /**
     * Checks whether the given map not empty or not
     *
     * @param input the input map
     * @return true if not empty else false
     */
    public static boolean isNotEmpty(@Nullable Map<?, ?> input) {
        return !isEmpty(input);
    }
}
