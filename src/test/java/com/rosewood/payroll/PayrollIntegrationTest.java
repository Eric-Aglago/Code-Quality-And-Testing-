package com.rosewood.payroll;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class PayrollIntegrationTest {

    private PayrollCalculator createCalculator() {
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

        return new PayrollCalculator(
                repository,
                grossPayCalculator,
                taxCalculator,
                reportPrinter
        );
    }

    @Test
    void calculatesNetPayUsingRealComponents() {
        BigDecimal result = createCalculator()
                .calculateNetPay("e1", 40, new BigDecimal("20.00"));

        assertEquals(new BigDecimal("760.00"), result);
    }

    @Test
    void calculatesOvertimePayUsingRealComponents() {
        BigDecimal result = createCalculator()
                .calculateNetPay("e2", 50, new BigDecimal("30.00"));

        assertEquals(new BigDecimal("1402.50"), result);
    }

    @Test
    void throwsExceptionForUnknownEmployeeUsingRealRepository() {
        EmployeeNotFoundException exception = assertThrows(
                EmployeeNotFoundException.class,
                () -> createCalculator().calculateNetPay(

                        "unknown", 40, new BigDecimal("20.00")
                )
        );

        assertEquals(
                "Employee not found with ID: unknown",
                exception.getMessage()
        );
    }
}