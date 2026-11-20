package com.rosewood.payroll;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class GrossPayCalculatorTest {

    private final GrossPayCalculator calculator = new GrossPayCalculator();

    @Test
    void calculatesGrossPayForStandardWorkingHours() {
        BigDecimal result = calculator.calculateGrossPay(
                40, new BigDecimal("20.00")
        );

        assertEquals(new BigDecimal("800.00"), result);
    }

    @Test
    void calculatesGrossPayWithOvertime() {
        BigDecimal result = calculator.calculateGrossPay(
                50, new BigDecimal("30.00")
        );

        assertEquals(new BigDecimal("1650.00"), result);
    }

    @Test
    void doesNotApplyOvertimeAtExactlyFortyHours() {
        BigDecimal result = calculator.calculateGrossPay(
                40, new BigDecimal("30.00")
        );

        assertEquals(new BigDecimal("1200.00"), result);
    }

    @Test
    void calculatesZeroGrossPayWhenNoHoursAreWorked() {
        BigDecimal result = calculator.calculateGrossPay(
                0, new BigDecimal("20.00")
        );

        assertEquals(new BigDecimal("0.00"), result);
    }

    @Test
    void rejectsNegativeHoursWorked() {
        assertEquals(
                IllegalArgumentException.class,
                assertThrows(
                        IllegalArgumentException.class,
                        () -> calculator.calculateGrossPay(
                                -5, new BigDecimal("20.00")
                        )
                ).getClass()
        );
    }

    @Test
    void rejectsNaNHoursWorked() {
        assertEquals(
                IllegalArgumentException.class,
                assertThrows(
                        IllegalArgumentException.class,
                        () -> calculator.calculateGrossPay(
                                Double.NaN, new BigDecimal("20.00")
                        )
                ).getClass()
        );
    }

    @Test
    void rejectsInfiniteHoursWorked() {
        assertEquals(
                IllegalArgumentException.class,
                assertThrows(
                        IllegalArgumentException.class,
                        () -> calculator.calculateGrossPay(
                                Double.POSITIVE_INFINITY,
                                new BigDecimal("20.00")
                        )
                ).getClass()
        );
    }

    @Test
    void rejectsNegativeHourlyRate() {
        assertEquals(
                IllegalArgumentException.class,
                assertThrows(
                        IllegalArgumentException.class,
                        () -> calculator.calculateGrossPay(
                                40, new BigDecimal("-20.00")
                        )
                ).getClass()
        );
    }

    @Test
    void rejectsNullHourlyRate() {
        assertEquals(
                IllegalArgumentException.class,
                assertThrows(
                        IllegalArgumentException.class,
                        () -> calculator.calculateGrossPay(40, null)
                ).getClass()
        );
    }
}