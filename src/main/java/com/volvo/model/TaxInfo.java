package com.volvo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@AllArgsConstructor
public class TaxInfo {
    private BigDecimal tax;
    private String currency;
}
