package com.rosewood.payroll;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class PayrollSummaryCalculator {

    public PayrollSummary calculate(List<PayrollResult> results) {
        Objects.requireNonNull(results, "Payroll results cannot be null");

        BigDecimal totalGrossPay = BigDecimal.ZERO;
        BigDecimal totalNetPay = BigDecimal.ZERO;

        for (PayrollResult result : results) {
            Objects.requireNonNull(result, "Payroll result cannot be null");

            totalGrossPay = totalGrossPay.add(result.grossPay());
            totalNetPay = totalNetPay.add(result.netPay());
        }

        return new PayrollSummary(totalGrossPay, totalNetPay);
    }
}
