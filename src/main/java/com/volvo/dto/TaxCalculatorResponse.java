package com.volvo.dto;

import com.volvo.util.CollectionUtil;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class TaxCalculatorResponse {
    private BigDecimal tax;
    private String taxWithCurrency;
    private Set<String> errors;

    public void addError(String errorMessage) {
        if (CollectionUtil.isEmpty(this.errors)) {
            this.errors = new HashSet<>();
        }
        this.errors.add(errorMessage);
    }

}
