package com.rosewood.payroll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class GrossPayCalculatorTest {

    private final GrossPayCalculator calculator = new GrossPayCalculator();

    @Test
    void calculatesGrossPayForStandardWorkingHours() {
        double result = calculator.calculateGrossPay(40, 20);

        assertEquals(800.0, result, 0.001);
    }

    @Test
    void calculatesGrossPayWithOvertime() {
        double result = calculator.calculateGrossPay(50, 30);

        assertEquals(1650.0, result, 0.001);
    }

    @Test
    void doesNotApplyOvertimeAtExactlyFortyHours() {
        double result = calculator.calculateGrossPay(40, 30);

        assertEquals(1200.0, result, 0.001);
    }

    @Test
    void calculatesZeroGrossPayWhenNoHoursAreWorked() {
        double result = calculator.calculateGrossPay(0, 20);

        assertEquals(0.0, result, 0.001);
    }
}