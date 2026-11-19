package com.rosewood.payroll;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HighTaxBand implements TaxBand {

    private static final BigDecimal MINIMUM_GROSS_PAY =
            new BigDecimal("5000.00");
    private static final BigDecimal TAX_RATE =
            new BigDecimal("0.25");

    @Override
    public boolean appliesTo(BigDecimal grossPay) {
        return grossPay.compareTo(MINIMUM_GROSS_PAY) >= 0;
    }

    @Override
    public BigDecimal calculateTax(BigDecimal grossPay) {
        return grossPay.multiply(TAX_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }
}