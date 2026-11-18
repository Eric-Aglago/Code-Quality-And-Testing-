package com.rosewood.payroll;
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
            calculator.calculateNetPay("e1", 40, 20);
            calculator.calculateNetPay("e2", 50, 30);
            calculator.calculateNetPay("unknown", 40, 20);

        } catch (EmployeeNotFoundException exception) {
            System.out.println(
                    "Payroll calculation failed: " + exception.getMessage()
            );
        }
    }
}