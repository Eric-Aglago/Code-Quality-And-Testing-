package com.rosewood.payroll;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class TaxCalculator {

    private final List<TaxBand> taxBands;

    public TaxCalculator(List<TaxBand> taxBands) {
        this.taxBands = List.copyOf(
                Objects.requireNonNull(taxBands, "Tax bands cannot be null")
        );

        if (this.taxBands.isEmpty()) {
            throw new IllegalArgumentException(
                    "At least one tax band must be configured"
            );
        }
    }

    public BigDecimal calculateNetPay(BigDecimal grossPay) {
        TaxBand applicableBand = taxBands.stream()
                .filter(band -> band.appliesTo(grossPay))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "No tax band found for gross pay: " + grossPay
                ));

        BigDecimal tax = applicableBand.calculateTax(grossPay);

        return grossPay.subtract(tax).setScale(2);
    }
}