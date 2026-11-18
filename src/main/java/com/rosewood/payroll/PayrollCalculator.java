package com.rosewood.payroll;

import java.util.Objects;

public class PayrollCalculator {

    private final EmployeeRepository repository;
    private final GrossPayCalculator grossPayCalculator;
    private final TaxCalculator taxCalculator;
    private final PayrollReportPrinter reportPrinter;

    public PayrollCalculator(
            EmployeeRepository repository,
            GrossPayCalculator grossPayCalculator,
            TaxCalculator taxCalculator,
            PayrollReportPrinter reportPrinter
    ) {
        this.repository = Objects.requireNonNull(
                repository,
                "Employee repository cannot be null"
        );
        this.grossPayCalculator = Objects.requireNonNull(
                grossPayCalculator,
                "Gross pay calculator cannot be null"
        );
        this.taxCalculator = Objects.requireNonNull(
                taxCalculator,
                "Tax calculator cannot be null"
        );
        this.reportPrinter = Objects.requireNonNull(
                reportPrinter,
                "Report printer cannot be null"
        );
    }

    public double calculateNetPay(
            String employeeId,
            double hoursWorked,
            double hourlyRate
    ) {
        validatePayrollInputs(employeeId, hoursWorked, hourlyRate);

        Employee employee = repository.findById(employeeId);

        if (employee == null) {
            throw new EmployeeNotFoundException(employeeId);
        }

        double grossPay = grossPayCalculator.calculateGrossPay(
                hoursWorked,
                hourlyRate
        );

        double netPay = taxCalculator.calculateNetPay(grossPay);

        reportPrinter.printNetPay(employee.getName(), netPay);

        return netPay;
    }

    private void validatePayrollInputs(
            String employeeId,
            double hoursWorked,
            double hourlyRate
    ) {
        if (employeeId == null || employeeId.isBlank()) {
            throw new IllegalArgumentException(
                    "Employee ID must not be blank"
            );
        }

        if (!Double.isFinite(hoursWorked) || hoursWorked < 0) {
            throw new IllegalArgumentException(
                    "Hours worked must be a finite, non-negative value"
            );
        }

        if (!Double.isFinite(hourlyRate) || hourlyRate < 0) {
            throw new IllegalArgumentException(
                    "Hourly rate must be a finite, non-negative value"
            );
        }
    }
}