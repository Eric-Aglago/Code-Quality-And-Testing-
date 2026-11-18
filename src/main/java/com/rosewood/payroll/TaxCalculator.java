package com.rosewood.payroll;

public class TaxCalculator {

    private static final double LOW_TAX_THRESHOLD = 1000;
    private static final double MEDIUM_TAX_THRESHOLD = 5000;

    private static final double LOW_TAX_RATE = 0.05;
    private static final double MEDIUM_TAX_RATE = 0.15;
    private static final double HIGH_TAX_RATE = 0.25;

    public double calculateNetPay(double grossPay) {
        double taxRate = getTaxRate(grossPay);
        return grossPay - (grossPay * taxRate);
    }

    private double getTaxRate(double grossPay) {
        if (grossPay < LOW_TAX_THRESHOLD) {
            return LOW_TAX_RATE;
        }

        if (grossPay < MEDIUM_TAX_THRESHOLD) {
            return MEDIUM_TAX_RATE;
        }

        return HIGH_TAX_RATE;
    }
}