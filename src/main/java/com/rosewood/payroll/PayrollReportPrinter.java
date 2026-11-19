package com.rosewood.payroll;

import java.math.BigDecimal;

public class PayrollReportPrinter {

    public void printNetPay(String employeeName, BigDecimal netPay) {
        System.out.println(
                "Net pay for " + employeeName + ": GHS "
                        + netPay.toPlainString()
        );
    }
}