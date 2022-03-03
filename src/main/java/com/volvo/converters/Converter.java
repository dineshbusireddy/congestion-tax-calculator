package com.volvo.converters;

public interface Converter<Source, Result> {
    Result convert(Source source);
}
