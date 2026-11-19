package com.rosewood.payroll;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class MediumTaxBandTest {

    private final MediumTaxBand mediumTaxBand = new MediumTaxBand();

    @Test
    void doesNotApplyBelowMinimumGrossPay() {
        assertFalse(mediumTaxBand.appliesTo(new BigDecimal("999.99")));
    }

    @Test
    void appliesAtMinimumGrossPay() {
        assertTrue(mediumTaxBand.appliesTo(new BigDecimal("1000.00")));
    }

    @Test
    void appliesBelowMaximumGrossPay() {
        assertTrue(mediumTaxBand.appliesTo(new BigDecimal("4999.99")));
    }

    @Test
    void doesNotApplyAtMaximumGrossPay() {
        assertFalse(mediumTaxBand.appliesTo(new BigDecimal("5000.00")));
    }
}