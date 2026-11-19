package com.rosewood.payroll;

import java.math.BigDecimal;
import java.util.ArrayList;
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

        List<PayrollResult> results = new ArrayList<>();

        try {
            results.add(calculator.calculatePayroll(
                    "e1", 40, new BigDecimal("20.00")
            ));

            results.add(calculator.calculatePayroll(
                    "e2", 50, new BigDecimal("30.00")
            ));

        } catch (EmployeeNotFoundException exception) {
            System.out.println(
                    "Payroll calculation failed: " + exception.getMessage()
            );
        }

        PayrollSummaryCalculator summaryCalculator =
                new PayrollSummaryCalculator();

        PayrollSummary summary = summaryCalculator.calculate(results);

        System.out.println("\n--- Payroll Summary ---");
        System.out.println("Total Gross Payroll: GHS "
                + summary.totalGrossPay());
        System.out.println("Total Net Payroll: GHS "
                + summary.totalNetPay());
    }
}