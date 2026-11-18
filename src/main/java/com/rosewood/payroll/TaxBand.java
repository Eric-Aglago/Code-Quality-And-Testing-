package com.rosewood.payroll;

public interface TaxBand {

    boolean appliesTo(double grossPay);

    double calculateTax(double grossPay);
}