package com.rosewood.payroll;

public class HighTaxBand implements TaxBand {

    private static final double MINIMUM_GROSS_PAY = 5000;
    private static final double TAX_RATE = 0.25;

    @Override
    public boolean appliesTo(double grossPay) {
        return grossPay >= MINIMUM_GROSS_PAY;
    }

    @Override
    public double calculateTax(double grossPay) {
        return grossPay * TAX_RATE;
    }
}