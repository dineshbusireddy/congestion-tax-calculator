package com.volvo.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.volvo.constants.Constants;
import com.volvo.exceptions.InvalidInputException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class DateDeserializer extends StdDeserializer<List<LocalDateTime>> {

    public DateDeserializer() {
        super(LocalDateTime.class);
    }

    @Override
    public List<LocalDateTime> deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException {
        List<LocalDateTime> result = new ArrayList<>();
        List<String> dates = jsonParser.readValueAs(List.class);

        for (String date : dates) {
            try {
                result.add(LocalDateTime.parse(date, Constants.DATE_TIME_FORMAT_PATTERN));
            } catch (DateTimeParseException e) {
                throw new InvalidInputException("Invalid Dates provided. " +
                        "Format should be either 2013-01-01 23:59:59 or 2013-01-01T23:59:59.000Z");
            }
        }

        return result;
    }

}
