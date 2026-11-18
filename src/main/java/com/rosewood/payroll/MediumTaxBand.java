package com.rosewood.payroll;

public class MediumTaxBand implements TaxBand {

    private static final double MINIMUM_GROSS_PAY = 1000;
    private static final double MAXIMUM_GROSS_PAY = 5000;
    private static final double TAX_RATE = 0.15;

    @Override
    public boolean appliesTo(double grossPay) {
        return grossPay >= MINIMUM_GROSS_PAY
                && grossPay < MAXIMUM_GROSS_PAY;
    }

    @Override
    public double calculateTax(double grossPay) {
        return grossPay * TAX_RATE;
    }
}
