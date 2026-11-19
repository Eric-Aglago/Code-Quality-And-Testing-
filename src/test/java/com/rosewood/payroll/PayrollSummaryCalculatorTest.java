package com.rosewood.payroll;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class PayrollSummaryCalculatorTest {

    private final PayrollSummaryCalculator calculator =
            new PayrollSummaryCalculator();

    @Test
    void calculatesTotalGrossAndNetPayroll() {
        List<PayrollResult> results = List.of(
                new PayrollResult(
                        new BigDecimal("800.00"),
                        new BigDecimal("760.00")
                ),
                new PayrollResult(
                        new BigDecimal("1650.00"),
                        new BigDecimal("1402.50")
                )
        );

        PayrollSummary summary = calculator.calculate(results);

        assertEquals(
                new BigDecimal("2450.00"),
                summary.totalGrossPay()
        );
        assertEquals(
                new BigDecimal("2162.50"),
                summary.totalNetPay()
        );
    }

    @Test
    void returnsZeroTotalsForEmptyPayroll() {
        PayrollSummary summary = calculator.calculate(List.of());

        assertEquals(BigDecimal.ZERO, summary.totalGrossPay());
        assertEquals(BigDecimal.ZERO, summary.totalNetPay());
    }
}