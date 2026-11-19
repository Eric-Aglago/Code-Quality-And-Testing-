package com.rosewood.payroll;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MediumTaxBand implements TaxBand {

    private static final BigDecimal MINIMUM_GROSS_PAY =
            new BigDecimal("1000.00");
    private static final BigDecimal MAXIMUM_GROSS_PAY =
            new BigDecimal("5000.00");
    private static final BigDecimal TAX_RATE =
            new BigDecimal("0.15");

    @Override
    public boolean appliesTo(BigDecimal grossPay) {
        return grossPay.compareTo(MINIMUM_GROSS_PAY) >= 0
                && grossPay.compareTo(MAXIMUM_GROSS_PAY) < 0;
    }

    @Override
    public BigDecimal calculateTax(BigDecimal grossPay) {
        return grossPay.multiply(TAX_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
