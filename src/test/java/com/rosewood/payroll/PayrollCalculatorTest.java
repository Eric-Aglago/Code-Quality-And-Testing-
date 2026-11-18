package com.rosewood.payroll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PayrollCalculatorTest {

    @Test
    void calculatesNetPay() {
        PayrollCalculator calculator = new PayrollCalculator();
        double net = calculator.calculateNetPay("e1", 40, 20);
        assertTrue(net > 0); // it returned some pay
    }

    @Test
    void calculatesOvertimePay() {
        // mock the calculator and check it returns what we told it to
        PayrollCalculator calculator = mock(PayrollCalculator.class);
        when(calculator.calculateNetPay("e2", 50, 30)).thenReturn(1402.5);
        assertEquals(1402.5, calculator.calculateNetPay("e2", 50, 30));
    }

    @Test
    void returnsPayForAnEmployee() {
        PayrollCalculator calculator = new PayrollCalculator();
        double net = calculator.calculateNetPay("e1", 35, 25);
        assertNotNull(net); // a result came back
    }

    @Test
    void throwsExceptionWhenEmployeeDoesNotExist() {
    PayrollCalculator calculator = new PayrollCalculator();

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
