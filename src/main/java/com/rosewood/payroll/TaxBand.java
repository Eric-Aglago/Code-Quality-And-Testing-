package com.rosewood.payroll;

import java.math.BigDecimal;

public interface TaxBand {

    boolean appliesTo(BigDecimal grossPay);

    BigDecimal calculateTax(BigDecimal grossPay);
}