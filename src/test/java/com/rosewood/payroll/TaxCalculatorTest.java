package com.rosewood.payroll;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class TaxCalculatorTest {

    @Test
    void calculatesNetPayUsingLowTaxBand() {
        TaxCalculator calculator = createCalculator();

        assertEquals(
                new BigDecimal("760.00"),
                calculator.calculateNetPay(new BigDecimal("800.00"))
        );
    }

    @Test
    void calculatesNetPayUsingMediumTaxBand() {
        TaxCalculator calculator = createCalculator();

        assertEquals(
                new BigDecimal("1275.00"),
                calculator.calculateNetPay(new BigDecimal("1500.00"))
        );
    }

    @Test
    void calculatesNetPayUsingHighTaxBand() {
        TaxCalculator calculator = createCalculator();

        assertEquals(
                new BigDecimal("3750.00"),
                calculator.calculateNetPay(new BigDecimal("5000.00"))
        );
    }

    @Test
    void selectsMediumTaxBandAtExactlyOneThousand() {
        TaxCalculator calculator = createCalculator();

        assertEquals(
                new BigDecimal("850.00"),
                calculator.calculateNetPay(new BigDecimal("1000.00"))
        );
    }

    @Test
    void calculatesNetPayJustAboveHighTaxBandThreshold() {
        TaxCalculator calculator = createCalculator();

        assertEquals(
                new BigDecimal("3750.07"),
                calculator.calculateNetPay(new BigDecimal("5000.10"))
        );
    }

    @Test
    void rejectsEmptyTaxBandConfiguration() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new TaxCalculator(List.of())
        );

        assertEquals(
                "At least one tax band must be configured",
                exception.getMessage()
        );
    }

    @Test
    void rejectsGrossPayWithoutAnApplicableTaxBand() {
        TaxCalculator calculator = new TaxCalculator(
                List.of(new LowTaxBand(), new MediumTaxBand())
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateNetPay(new BigDecimal("6000.00"))
        );

        assertEquals(
                "No tax band found for gross pay: 6000.00",
                exception.getMessage()
        );
    }

    @Test
    void supportsNewTaxBandWithoutModifyingTaxCalculator() {
        TaxBand specialTaxBand = new TaxBand() {
            @Override
            public boolean appliesTo(BigDecimal grossPay) {
                return grossPay.compareTo(new BigDecimal("10000.00")) >= 0;
            }

            @Override
            public BigDecimal calculateTax(BigDecimal grossPay) {
                return grossPay.multiply(new BigDecimal("0.30"))
                        .setScale(2);
            }
        };

        TaxCalculator calculator = new TaxCalculator(
                List.of(
                        new LowTaxBand(),
                        new MediumTaxBand(),
                        specialTaxBand,
                        new HighTaxBand()
                )
        );

        assertEquals(
                new BigDecimal("7000.00"),
                calculator.calculateNetPay(new BigDecimal("10000.00"))
        );
    }

    private TaxCalculator createCalculator() {
        return new TaxCalculator(
                List.of(
                        new LowTaxBand(),
                        new MediumTaxBand(),
                        new HighTaxBand()
                )
        );
    }
}