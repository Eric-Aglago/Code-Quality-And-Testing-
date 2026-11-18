package com.rosewood.payroll;

public class LowTaxBand implements TaxBand {

    private static final double MAXIMUM_GROSS_PAY = 1000;
    private static final double TAX_RATE = 0.05;

    @Override
    public boolean appliesTo(double grossPay) {
        return grossPay < MAXIMUM_GROSS_PAY;
    }

    @Override
    public double calculateTax(double grossPay) {
        return grossPay * TAX_RATE;
    }
}