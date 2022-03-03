package com.volvo.converters;

/**
 * Converts the provided Source object to Result object
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
public interface Converter<Source, Result> {

    /**
     * Converts the provided Source object to Result object
     *
     * @param source the Source object
     * @return the Result object
     */
    Result convert(Source source);
}
