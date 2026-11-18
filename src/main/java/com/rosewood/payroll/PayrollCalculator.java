package com.rosewood.payroll;

public class PayrollCalculator {

    private final EmployeeRepository repository = new InMemoryEmployeeRepository();

    public double calculateNetPay(String employeeId, double hoursWorked, double hourlyRate) {
        Employee employee = repository.findById(employeeId);

        if (employee == null) {
            throw new EmployeeNotFoundException(employeeId);
        }

        double gross = hoursWorked * hourlyRate;

        // Overtime hours receive an additional 50% premium.
        if (hoursWorked > 40) {
            gross = gross + (hoursWorked - 40) * hourlyRate * 0.5;
        }

        double taxRate;
        if (gross < 1000) {
            taxRate = 0.05;
        } else if (gross < 5000) {
            taxRate = 0.15;
        } else {
            taxRate = 0.25;
        }

        double net = gross - (gross * taxRate);

        System.out.println("Net pay for " + employee.getName() + ": GHS " + net);

        return net;
    }
}
