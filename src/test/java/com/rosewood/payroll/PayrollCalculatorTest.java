package com.rosewood.payroll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class PayrollCalculatorTest {

    private PayrollCalculator createCalculator(
            EmployeeRepository repository
    ) {
        return new PayrollCalculator(
                repository,
                new GrossPayCalculator(),
                new TaxCalculator(),
                new PayrollReportPrinter()
        );
    }

    @Test
    void calculatesNetPayForRegularHours() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("e1"))
                .thenReturn(new Employee("e1", "Efua Sarpong"));

        PayrollCalculator calculator = createCalculator(repository);

        double netPay = calculator.calculateNetPay("e1", 40, 20);

        assertEquals(760.0, netPay, 0.001);
    }

    @Test
    void calculatesNetPayIncludingOvertime() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("e1"))
                .thenReturn(new Employee("e1", "Efua Sarpong"));

        PayrollCalculator calculator = createCalculator(repository);

        double netPay = calculator.calculateNetPay("e1", 50, 30);

        assertEquals(1402.5, netPay, 0.001);
    }

    @Test
    void calculatesNetPayForLowerTaxBand() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("e1"))
                .thenReturn(new Employee("e1", "Efua Sarpong"));

        PayrollCalculator calculator = createCalculator(repository);

        double netPay = calculator.calculateNetPay("e1", 35, 25);

        assertEquals(831.25, netPay, 0.001);
    }

    @Test
    void throwsExceptionWhenEmployeeDoesNotExist() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("unknown")).thenReturn(null);

        PayrollCalculator calculator = createCalculator(repository);

        EmployeeNotFoundException exception = assertThrows(
                EmployeeNotFoundException.class,
                () -> calculator.calculateNetPay("unknown", 40, 20)
        );

        assertEquals(
                "Employee not found with ID: unknown",
                exception.getMessage()
        );
    }

    @Test
    void rejectsNullEmployeeId() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        PayrollCalculator calculator = createCalculator(repository);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateNetPay(null, 40, 20)
        );

        assertEquals("Employee ID must not be blank", exception.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void rejectsBlankEmployeeId() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        PayrollCalculator calculator = createCalculator(repository);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateNetPay("  ", 40, 20)
        );

        assertEquals("Employee ID must not be blank", exception.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void rejectsNegativeHoursWorked() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        PayrollCalculator calculator = createCalculator(repository);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateNetPay("e1", -5, 20)
        );

        assertEquals(
                "Hours worked must be a finite, non-negative value",
                exception.getMessage()
        );
        verifyNoInteractions(repository);
    }

    @Test
    void rejectsNegativeHourlyRate() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        PayrollCalculator calculator = createCalculator(repository);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateNetPay("e1", 40, -20)
        );

        assertEquals(
                "Hourly rate must be a finite, non-negative value",
                exception.getMessage()
        );
        verifyNoInteractions(repository);
    }

    @Test
    void rejectsNonFiniteHoursWorked() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        PayrollCalculator calculator = createCalculator(repository);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateNetPay("e1", Double.NaN, 20)
        );

        assertEquals(
                "Hours worked must be a finite, non-negative value",
                exception.getMessage()
        );
        verifyNoInteractions(repository);
    }

    @Test
    void rejectsNonFiniteHourlyRate() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        PayrollCalculator calculator = createCalculator(repository);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateNetPay(
                        "e1",
                        40,
                        Double.POSITIVE_INFINITY
                )
        );

        assertEquals(
                "Hourly rate must be a finite, non-negative value",
                exception.getMessage()
        );
        verifyNoInteractions(repository);
    }

    @Test
    void acceptsZeroHoursWorked() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("e1"))
                .thenReturn(new Employee("e1", "Efua Sarpong"));

        PayrollCalculator calculator = createCalculator(repository);

        double netPay = calculator.calculateNetPay("e1", 0, 20);

        assertEquals(0.0, netPay, 0.001);
    }

    @Test
    void acceptsZeroHourlyRate() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("e1"))
                .thenReturn(new Employee("e1", "Efua Sarpong"));

        PayrollCalculator calculator = createCalculator(repository);

        double netPay = calculator.calculateNetPay("e1", 40, 0);

        assertEquals(0.0, netPay, 0.001);
    }
}