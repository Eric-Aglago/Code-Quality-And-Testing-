package com.rosewood.payroll;

import java.math.BigDecimal;
import java.util.Objects;

public record PayrollSummary(
        BigDecimal totalGrossPay,
        BigDecimal totalNetPay
) {
    public PayrollSummary {
        Objects.requireNonNull(
                totalGrossPay, "Total gross pay cannot be null"
        );
        Objects.requireNonNull(
                totalNetPay, "Total net pay cannot be null"
        );
    }
}