package com.rosewood.payroll;

public class GrossPayCalculator {

    private static final double STANDARD_HOURS = 40;
    private static final double OVERTIME_PREMIUM = 0.5;

    public double calculateGrossPay(
            double hoursWorked,
            double hourlyRate
    ) {
        double grossPay = hoursWorked * hourlyRate;

        if (hoursWorked > STANDARD_HOURS) {
            double overtimeHours = hoursWorked - STANDARD_HOURS;
            grossPay += overtimeHours * hourlyRate * OVERTIME_PREMIUM;
        }

        return grossPay;
    }
}