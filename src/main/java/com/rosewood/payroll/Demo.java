package com.rosewood.payroll;

import java.math.BigDecimal;
import java.util.List;

public class Demo {

    public static void main(String[] args) {

        EmployeeRepository repository = new InMemoryEmployeeRepository();

        GrossPayCalculator grossPayCalculator = new GrossPayCalculator();

        TaxCalculator taxCalculator = new TaxCalculator(
                List.of(
                        new LowTaxBand(),
                        new MediumTaxBand(),
                        new HighTaxBand()
                )
        );

        PayrollReportPrinter reportPrinter = new PayrollReportPrinter();

        PayrollCalculator calculator = new PayrollCalculator(
                repository,
                grossPayCalculator,
                taxCalculator,
                reportPrinter
        );

        try {
            BigDecimal firstRate = new BigDecimal("20.00");
            BigDecimal secondRate = new BigDecimal("30.00");

            calculator.calculateNetPay("e1", 40, firstRate);
            calculator.calculateNetPay("e2", 50, secondRate);
            calculator.calculateNetPay("unknown", 40, firstRate);

        } catch (EmployeeNotFoundException exception) {
            System.out.println(
                    "Payroll calculation failed: " + exception.getMessage()
            );
        }
    }
}