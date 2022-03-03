package com.volvo.calculators;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TaxCalculator {
    BigDecimal calculateTax(LocalDateTime date);
}
