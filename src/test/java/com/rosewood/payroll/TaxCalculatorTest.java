package com.rosewood.payroll;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class TaxCalculatorTest {

    private TaxCalculator createCalculator() {
        return new TaxCalculator(
                List.of(
                        new LowTaxBand(),
                        new MediumTaxBand(),
                        new HighTaxBand()
                )
        );
    }

    @Test
    void appliesFivePercentTaxToLowIncome() {
        assertEquals(
                760.0,
                createCalculator().calculateNetPay(800),
                0.001
        );
    }

    @Test
    void appliesFifteenPercentTaxToMediumIncome() {
        assertEquals(
                1275.0,
                createCalculator().calculateNetPay(1500),
                0.001
        );
    }

    @Test
    void appliesTwentyFivePercentTaxToHighIncome() {
        assertEquals(
                4500.0,
                createCalculator().calculateNetPay(6000),
                0.001
        );
    }

    @Test
    void appliesMediumTaxAtExactlyOneThousand() {
        assertEquals(
                850.0,
                createCalculator().calculateNetPay(1000),
                0.001
        );
    }

    @Test
    void appliesHighTaxAtExactlyFiveThousand() {
        assertEquals(
                3750.0,
                createCalculator().calculateNetPay(5000),
                0.001
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
    void rejectsGrossPayWithoutMatchingTaxBand() {
        TaxCalculator calculator = new TaxCalculator(
                List.of(new MediumTaxBand())
        );

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateNetPay(500)
        );

        assertEquals(
                "No tax band found for gross pay: 500.0",
                exception.getMessage()
        );
    }
    @Test
void supportsNewTaxBandWithoutModifyingTaxCalculator() {
    TaxBand specialTaxBand = new TaxBand() {

        @Override
        public boolean appliesTo(double grossPay) {
            return grossPay >= 10000;
        }

        @Override
        public double calculateTax(double grossPay) {
            return grossPay * 0.30;
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

    assertEquals(7000.0, calculator.calculateNetPay(10000), 0.001);
}
}