package com.rosewood.payroll;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;

class PayrollCalculatorTest {

    private PayrollCalculator createCalculator(
            EmployeeRepository repository,
            GrossPayCalculator grossPayCalculator,
            TaxCalculator taxCalculator,
            PayrollReportPrinter reportPrinter
    ) {
        return new PayrollCalculator(
                repository,
                grossPayCalculator,
                taxCalculator,
                reportPrinter
        );
    }

    @Test
    void calculatesPayrollForRegularHours() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        GrossPayCalculator grossCalculator = mock(GrossPayCalculator.class);
        TaxCalculator taxCalculator = mock(TaxCalculator.class);
        PayrollReportPrinter printer = mock(PayrollReportPrinter.class);

        Employee employee = new Employee("e1", "Efua Sarpong");
        BigDecimal grossPay = new BigDecimal("800.00");
        BigDecimal netPay = new BigDecimal("760.00");

        when(repository.findById("e1")).thenReturn(employee);
        when(grossCalculator.calculateGrossPay(
                40, new BigDecimal("20.00")
        )).thenReturn(grossPay);
        when(taxCalculator.calculateNetPay(grossPay))
                .thenReturn(netPay);

        PayrollResult result = createCalculator(
                repository, grossCalculator, taxCalculator, printer
        ).calculatePayroll("e1", 40, new BigDecimal("20.00"));

        assertEquals(grossPay, result.grossPay());
        assertEquals(netPay, result.netPay());

        verify(repository).findById("e1");
        verify(grossCalculator).calculateGrossPay(
                40, new BigDecimal("20.00")
        );
        verify(taxCalculator).calculateNetPay(grossPay);
        verify(printer).printNetPay("Efua Sarpong", netPay);
    }

    @Test
    void calculatesPayrollIncludingOvertime() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        GrossPayCalculator grossCalculator = mock(GrossPayCalculator.class);
        TaxCalculator taxCalculator = mock(TaxCalculator.class);
        PayrollReportPrinter printer = mock(PayrollReportPrinter.class);

        Employee employee = new Employee("e1", "Efua Sarpong");
        BigDecimal grossPay = new BigDecimal("1650.00");
        BigDecimal netPay = new BigDecimal("1402.50");

        when(repository.findById("e1")).thenReturn(employee);
        when(grossCalculator.calculateGrossPay(
                50, new BigDecimal("30.00")
        )).thenReturn(grossPay);
        when(taxCalculator.calculateNetPay(grossPay))
                .thenReturn(netPay);

        PayrollResult result = createCalculator(
                repository, grossCalculator, taxCalculator, printer
        ).calculatePayroll("e1", 50, new BigDecimal("30.00"));

        assertEquals(grossPay, result.grossPay());
        assertEquals(netPay, result.netPay());

        verify(printer).printNetPay("Efua Sarpong", netPay);
    }

    @Test
    void calculatesPayrollForLowerTaxBand() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        GrossPayCalculator grossCalculator = mock(GrossPayCalculator.class);
        TaxCalculator taxCalculator = mock(TaxCalculator.class);
        PayrollReportPrinter printer = mock(PayrollReportPrinter.class);

        Employee employee = new Employee("e1", "Efua Sarpong");
        BigDecimal grossPay = new BigDecimal("875.00");
        BigDecimal netPay = new BigDecimal("831.25");

        when(repository.findById("e1")).thenReturn(employee);
        when(grossCalculator.calculateGrossPay(
                35, new BigDecimal("25.00")
        )).thenReturn(grossPay);
        when(taxCalculator.calculateNetPay(grossPay))
                .thenReturn(netPay);

        PayrollResult result = createCalculator(
                repository, grossCalculator, taxCalculator, printer
        ).calculatePayroll("e1", 35, new BigDecimal("25.00"));

        assertEquals(grossPay, result.grossPay());
        assertEquals(netPay, result.netPay());

        verify(printer).printNetPay("Efua Sarpong", netPay);
    }

    @Test
    void throwsExceptionWhenEmployeeDoesNotExist() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        GrossPayCalculator grossCalculator = mock(GrossPayCalculator.class);
        TaxCalculator taxCalculator = mock(TaxCalculator.class);
        PayrollReportPrinter printer = mock(PayrollReportPrinter.class);

        when(repository.findById("unknown")).thenReturn(null);

        EmployeeNotFoundException exception = assertThrows(
                EmployeeNotFoundException.class,
                () -> createCalculator(
                        repository, grossCalculator, taxCalculator, printer
                ).calculatePayroll(
                        "unknown", 40, new BigDecimal("20.00")
                )
        );

        assertEquals(
                "Employee not found with ID: unknown",
                exception.getMessage()
        );

        verifyNoInteractions(grossCalculator, taxCalculator, printer);
    }

    @Test
    void rejectsNullEmployeeId() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        GrossPayCalculator grossCalculator = mock(GrossPayCalculator.class);
        TaxCalculator taxCalculator = mock(TaxCalculator.class);
        PayrollReportPrinter printer = mock(PayrollReportPrinter.class);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createCalculator(
                        repository, grossCalculator, taxCalculator, printer
                ).calculatePayroll(null, 40, new BigDecimal("20.00"))
        );

        assertEquals("Employee ID must not be blank", exception.getMessage());

        verifyNoInteractions(
                repository, grossCalculator, taxCalculator, printer
        );
    }

    @Test
    void rejectsBlankEmployeeId() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        GrossPayCalculator grossCalculator = mock(GrossPayCalculator.class);
        TaxCalculator taxCalculator = mock(TaxCalculator.class);
        PayrollReportPrinter printer = mock(PayrollReportPrinter.class);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createCalculator(
                        repository, grossCalculator, taxCalculator, printer
                ).calculatePayroll("  ", 40, new BigDecimal("20.00"))
        );

        assertEquals("Employee ID must not be blank", exception.getMessage());

        verifyNoInteractions(
                repository, grossCalculator, taxCalculator, printer
        );
    }

    @Test
    void rejectsNegativeHoursWorked() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        GrossPayCalculator grossCalculator = mock(GrossPayCalculator.class);
        TaxCalculator taxCalculator = mock(TaxCalculator.class);
        PayrollReportPrinter printer = mock(PayrollReportPrinter.class);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createCalculator(
                        repository, grossCalculator, taxCalculator, printer
                ).calculatePayroll("e1", -5, new BigDecimal("20.00"))
        );

        assertEquals(
                "Hours worked must be a finite, non-negative value",
                exception.getMessage()
        );

        verifyNoInteractions(
                repository, grossCalculator, taxCalculator, printer
        );
    }

    @Test
    void rejectsNegativeHourlyRate() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        GrossPayCalculator grossCalculator = mock(GrossPayCalculator.class);
        TaxCalculator taxCalculator = mock(TaxCalculator.class);
        PayrollReportPrinter printer = mock(PayrollReportPrinter.class);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createCalculator(
                        repository, grossCalculator, taxCalculator, printer
                ).calculatePayroll("e1", 40, new BigDecimal("-20.00"))
        );

        assertEquals(
                "Hourly rate must be non-negative and cannot be null",
                exception.getMessage()
        );

        verifyNoInteractions(
                repository, grossCalculator, taxCalculator, printer
        );
    }

    @Test
    void rejectsNonFiniteHoursWorked() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        GrossPayCalculator grossCalculator = mock(GrossPayCalculator.class);
        TaxCalculator taxCalculator = mock(TaxCalculator.class);
        PayrollReportPrinter printer = mock(PayrollReportPrinter.class);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createCalculator(
                        repository, grossCalculator, taxCalculator, printer
                ).calculatePayroll(
                        "e1", Double.NaN, new BigDecimal("20.00")
                )
        );

        assertEquals(
                "Hours worked must be a finite, non-negative value",
                exception.getMessage()
        );

        verifyNoInteractions(
                repository, grossCalculator, taxCalculator, printer
        );
    }

    @Test
    void rejectsNullHourlyRate() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        GrossPayCalculator grossCalculator = mock(GrossPayCalculator.class);
        TaxCalculator taxCalculator = mock(TaxCalculator.class);
        PayrollReportPrinter printer = mock(PayrollReportPrinter.class);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> createCalculator(
                        repository, grossCalculator, taxCalculator, printer
                ).calculatePayroll("e1", 40, null)
        );

        assertEquals(
                "Hourly rate must be non-negative and cannot be null",
                exception.getMessage()
        );

        verifyNoInteractions(
                repository, grossCalculator, taxCalculator, printer
        );
    }

    @Test
    void acceptsZeroHoursWorked() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        GrossPayCalculator grossCalculator = mock(GrossPayCalculator.class);
        TaxCalculator taxCalculator = mock(TaxCalculator.class);
        PayrollReportPrinter printer = mock(PayrollReportPrinter.class);

        Employee employee = new Employee("e1", "Efua Sarpong");
        BigDecimal grossPay = new BigDecimal("0.00");
        BigDecimal netPay = new BigDecimal("0.00");

        when(repository.findById("e1")).thenReturn(employee);
        when(grossCalculator.calculateGrossPay(
                0, new BigDecimal("20.00")
        )).thenReturn(grossPay);
        when(taxCalculator.calculateNetPay(grossPay))
                .thenReturn(netPay);

        PayrollResult result = createCalculator(
                repository, grossCalculator, taxCalculator, printer
        ).calculatePayroll("e1", 0, new BigDecimal("20.00"));

        assertEquals(grossPay, result.grossPay());
        assertEquals(netPay, result.netPay());
    }

    @Test
    void acceptsZeroHourlyRate() {
        EmployeeRepository repository = mock(EmployeeRepository.class);
        GrossPayCalculator grossCalculator = mock(GrossPayCalculator.class);
        TaxCalculator taxCalculator = mock(TaxCalculator.class);
        PayrollReportPrinter printer = mock(PayrollReportPrinter.class);

        Employee employee = new Employee("e1", "Efua Sarpong");
        BigDecimal grossPay = new BigDecimal("0.00");
        BigDecimal netPay = new BigDecimal("0.00");

        when(repository.findById("e1")).thenReturn(employee);
        when(grossCalculator.calculateGrossPay(
                40, BigDecimal.ZERO
        )).thenReturn(grossPay);
        when(taxCalculator.calculateNetPay(grossPay))
                .thenReturn(netPay);

        PayrollResult result = createCalculator(
                repository, grossCalculator, taxCalculator, printer
        ).calculatePayroll("e1", 40, BigDecimal.ZERO);

        assertEquals(grossPay, result.grossPay());
        assertEquals(netPay, result.netPay());
    }
}