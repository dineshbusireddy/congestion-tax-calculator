package com.volvo.converters;

import com.volvo.constants.Constants;
import com.volvo.dto.TaxCalculatorResponse;
import com.volvo.model.TaxInfo;
import org.springframework.stereotype.Component;

/**
 * Converts from {@link TaxInfo} to {@link TaxCalculatorResponse}
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
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
