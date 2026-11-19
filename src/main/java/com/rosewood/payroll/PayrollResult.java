package com.rosewood.payroll;

import java.math.BigDecimal;
import java.util.Objects;

public record PayrollResult(
        BigDecimal grossPay,
        BigDecimal netPay
) {
    public PayrollResult {
        Objects.requireNonNull(grossPay, "Gross pay cannot be null");
        Objects.requireNonNull(netPay, "Net pay cannot be null");
    }
}