package com.rosewood.payroll;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GrossPayCalculator {

    private static final double STANDARD_HOURS = 40;
    private static final BigDecimal OVERTIME_PREMIUM =
            new BigDecimal("0.5");
    private static final int CURRENCY_SCALE = 2;

    public BigDecimal calculateGrossPay(
            double hoursWorked,
            BigDecimal hourlyRate
    ) {
        if (!Double.isFinite(hoursWorked) || hoursWorked < 0) {
            throw new IllegalArgumentException(
                    "Hours worked must be a finite, non-negative value"
            );
        }

        if (hourlyRate == null || hourlyRate.signum() < 0) {
            throw new IllegalArgumentException(
                    "Hourly rate must be non-negative and cannot be null"
            );
        }

        BigDecimal hours = BigDecimal.valueOf(hoursWorked);

        BigDecimal grossPay = hours.multiply(hourlyRate);

        if (hoursWorked > STANDARD_HOURS) {
            BigDecimal overtimeHours = hours.subtract(
                    BigDecimal.valueOf(STANDARD_HOURS)
            );

            BigDecimal overtimePay = overtimeHours
                    .multiply(hourlyRate)
                    .multiply(OVERTIME_PREMIUM);

            grossPay = grossPay.add(overtimePay);
        }

        return grossPay.setScale(CURRENCY_SCALE, RoundingMode.HALF_UP);
    }
}