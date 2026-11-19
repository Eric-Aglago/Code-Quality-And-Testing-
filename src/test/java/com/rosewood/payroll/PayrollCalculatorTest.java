package com.rosewood.payroll;

import java.math.BigDecimal;
import java.util.List;

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
                new TaxCalculator(
                        List.of(
                                new LowTaxBand(),
                                new MediumTaxBand(),
                                new HighTaxBand()
                        )
                ),
                new PayrollReportPrinter()
        );
    }

    @Test
    void calculatesPayrollForRegularHours() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("e1"))
                .thenReturn(new Employee("e1", "Efua Sarpong"));

        PayrollResult result = createCalculator(repository)
                .calculatePayroll("e1", 40, new BigDecimal("20.00"));

        assertEquals(new BigDecimal("800.00"), result.grossPay());
        assertEquals(new BigDecimal("760.00"), result.netPay());
    }

    @Test
    void calculatesPayrollIncludingOvertime() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("e1"))
                .thenReturn(new Employee("e1", "Efua Sarpong"));

        PayrollResult result = createCalculator(repository)
                .calculatePayroll("e1", 50, new BigDecimal("30.00"));

        assertEquals(new BigDecimal("1650.00"), result.grossPay());
        assertEquals(new BigDecimal("1402.50"), result.netPay());
    }

    @Test
    void calculatesPayrollForLowerTaxBand() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("e1"))
                .thenReturn(new Employee("e1", "Efua Sarpong"));

        PayrollResult result = createCalculator(repository)
                .calculatePayroll("e1", 35, new BigDecimal("25.00"));

        assertEquals(new BigDecimal("875.00"), result.grossPay());
        assertEquals(new BigDecimal("831.25"), result.netPay());
    }

    @Test
    void throwsExceptionWhenEmployeeDoesNotExist() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("unknown")).thenReturn(null);

        EmployeeNotFoundException exception = assertThrows(
                EmployeeNotFoundException.class,
                () -> createCalculator(repository).calculatePayroll(
                        "unknown", 40, new BigDecimal("20.00")
                )
        );

        assertEquals(
                "Employee not found with ID: unknown",
                exception.getMessage()
        );
    }

    @Test
    void rejectsNullEmployeeId() {
        EmployeeRepository repository = mock(EmployeeRepository.class);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createCalculator(repository).calculatePayroll(
                        null, 40, new BigDecimal("20.00")
                )
        );

        assertEquals("Employee ID must not be blank", exception.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void rejectsBlankEmployeeId() {
        EmployeeRepository repository = mock(EmployeeRepository.class);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createCalculator(repository).calculatePayroll(
                        "  ", 40, new BigDecimal("20.00")
                )
        );

        assertEquals("Employee ID must not be blank", exception.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void rejectsNegativeHoursWorked() {
        EmployeeRepository repository = mock(EmployeeRepository.class);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createCalculator(repository).calculatePayroll(
                        "e1", -5, new BigDecimal("20.00")
                )
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

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createCalculator(repository).calculatePayroll(
                        "e1", 40, new BigDecimal("-20.00")
                )
        );

        assertEquals(
                "Hourly rate must be non-negative and cannot be null",
                exception.getMessage()
        );
        verifyNoInteractions(repository);
    }

    @Test
    void rejectsNonFiniteHoursWorked() {
        EmployeeRepository repository = mock(EmployeeRepository.class);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createCalculator(repository).calculatePayroll(
                        "e1", Double.NaN, new BigDecimal("20.00")
                )
        );

        assertEquals(
                "Hours worked must be a finite, non-negative value",
                exception.getMessage()
        );
        verifyNoInteractions(repository);
    }

    @Test
    void rejectsNullHourlyRate() {
        EmployeeRepository repository = mock(EmployeeRepository.class);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createCalculator(repository).calculatePayroll(
                        "e1", 40, null
                )
        );

        assertEquals(
                "Hourly rate must be non-negative and cannot be null",
                exception.getMessage()
        );
        verifyNoInteractions(repository);
    }

    @Test
    void acceptsZeroHoursWorked() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("e1"))
                .thenReturn(new Employee("e1", "Efua Sarpong"));

        PayrollResult result = createCalculator(repository)
                .calculatePayroll("e1", 0, new BigDecimal("20.00"));

        assertEquals(new BigDecimal("0.00"), result.grossPay());
        assertEquals(new BigDecimal("0.00"), result.netPay());
    }

    @Test
    void acceptsZeroHourlyRate() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        when(repository.findById("e1"))
                .thenReturn(new Employee("e1", "Efua Sarpong"));

        PayrollResult result = createCalculator(repository)
                .calculatePayroll("e1", 40, BigDecimal.ZERO);

        assertEquals(new BigDecimal("0.00"), result.grossPay());
        assertEquals(new BigDecimal("0.00"), result.netPay());
    }
}