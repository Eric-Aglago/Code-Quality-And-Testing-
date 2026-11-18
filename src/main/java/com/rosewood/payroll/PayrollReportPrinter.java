package com.rosewood.payroll;

public class PayrollReportPrinter {

    public void printNetPay(String employeeName, double netPay) {
        System.out.println(
                "Net pay for " + employeeName + ": GHS " + netPay
        );
    }
}