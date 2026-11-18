package com.rosewood.payroll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

class PayrollCalculatorTest {

    @Test
    void calculatesNetPayForRegularHours() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("e1"))
                .thenReturn(new Employee("e1", "Efua Sarpong"));

        PayrollCalculator calculator = new PayrollCalculator(repository);

        double net = calculator.calculateNetPay("e1", 40, 20);

        assertEquals(760.0, net, 0.001);
    }

    @Test
    void calculatesNetPayIncludingOvertime() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("e2"))
                .thenReturn(new Employee("e2", "Kwame Owusu"));

        PayrollCalculator calculator = new PayrollCalculator(repository);

        double net = calculator.calculateNetPay("e2", 50, 30);

        assertEquals(1402.5, net, 0.001);
    }

    @Test
    void calculatesNetPayForLessThanFortyHours() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("e1"))
                .thenReturn(new Employee("e1", "Efua Sarpong"));

        PayrollCalculator calculator = new PayrollCalculator(repository);

        double net = calculator.calculateNetPay("e1", 35, 25);

        assertEquals(831.25, net, 0.001);
    }

    @Test
    void throwsExceptionWhenEmployeeDoesNotExist() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("unknown")).thenReturn(null);

        PayrollCalculator calculator = new PayrollCalculator(repository);

        EmployeeNotFoundException exception = assertThrows(
                EmployeeNotFoundException.class,
                () -> calculator.calculateNetPay("unknown", 40, 20)
        );

        assertEquals(
                "Employee not found with ID: unknown",
                exception.getMessage()
        );
    }
}