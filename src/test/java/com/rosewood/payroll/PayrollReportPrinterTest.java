package com.rosewood.payroll;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
class PayrollReportPrinterTest {

    private final ByteArrayOutputStream outputStream =
            new ByteArrayOutputStream();

    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void printsEmployeeNameCurrencyAndNetPay() {
        PayrollReportPrinter printer = new PayrollReportPrinter();

        printer.printNetPay("Emmanuel", new BigDecimal("1500.00"));

        String output = outputStream.toString(StandardCharsets.UTF_8);

        assertTrue(output.contains("Emmanuel"));
        assertTrue(output.contains("GHS"));
        assertTrue(output.contains("1500.00"));
    }
}