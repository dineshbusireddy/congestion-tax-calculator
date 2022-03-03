package com.volvo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Holds tax and currency info
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
@Getter
@AllArgsConstructor
public class TaxInfo {
    private BigDecimal tax;
    private String currency;
}
