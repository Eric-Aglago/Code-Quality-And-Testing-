package com.rosewood.payroll;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LowTaxBand implements TaxBand {

    private static final BigDecimal MAXIMUM_GROSS_PAY =
            new BigDecimal("1000.00");
    private static final BigDecimal TAX_RATE =
            new BigDecimal("0.05");

    @Override
    public boolean appliesTo(BigDecimal grossPay) {
        return grossPay.compareTo(MAXIMUM_GROSS_PAY) < 0;
    }

    @Override
    public BigDecimal calculateTax(BigDecimal grossPay) {
        return grossPay.multiply(TAX_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }
}