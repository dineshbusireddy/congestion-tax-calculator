package com.volvo.converters;

import com.volvo.constants.Constants;
import com.volvo.constants.TestConstants;
import com.volvo.dto.TaxCalculatorResponse;
import com.volvo.model.TaxInfo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TaxInfoToTaxCalculatorResponseConverterTest {

    @InjectMocks
    private TaxInfoToTaxCalculatorResponseConverter responseConverter;

    @Test
    void convert() {
        TaxCalculatorResponse response = responseConverter.
                convert(new TaxInfo(BigDecimal.TEN, TestConstants.CURRENCY));
        assertEquals(BigDecimal.TEN, response.getTax());
        assertEquals(BigDecimal.TEN + Constants.SPACE +
                TestConstants.CURRENCY, response.getTaxWithCurrency());
    }
}