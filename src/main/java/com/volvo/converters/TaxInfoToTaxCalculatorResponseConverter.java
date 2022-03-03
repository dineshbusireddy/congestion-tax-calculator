package com.volvo.converters;

import com.volvo.constants.Constants;
import com.volvo.dto.TaxCalculatorResponse;
import com.volvo.model.TaxInfo;
import org.springframework.stereotype.Component;

@Component
public class TaxInfoToTaxCalculatorResponseConverter implements Converter<TaxInfo, TaxCalculatorResponse> {

    @Override
    public TaxCalculatorResponse convert(TaxInfo taxInfo) {
        TaxCalculatorResponse response = new TaxCalculatorResponse();
        response.setTax(taxInfo.getTax());
        response.setTaxWithCurrency(taxInfo.getTax() + Constants.SPACE + taxInfo.getCurrency());

        return response;
    }

}
