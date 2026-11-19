package com.rosewood.payroll;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class HighTaxBandTest {

    private final HighTaxBand highTaxBand = new HighTaxBand();

    @Test
    void appliesToGrossPayAtMinimumThreshold() {
        assertTrue(highTaxBand.appliesTo(new BigDecimal("5000.00")));
    }

    @Test
    void doesNotApplyToGrossPayBelowMinimumThreshold() {
        assertFalse(highTaxBand.appliesTo(new BigDecimal("4999.99")));
    }
}