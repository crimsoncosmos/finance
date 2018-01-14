package com.danandreicarp.projects.finance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:carpdan@amazon.com">Dan Andrei Carp</a>
 */
class AppTest {

    private App app;

    @BeforeEach
    void setUp() {
        app = new App();
        app.setMonthlyContribution(BigDecimal.valueOf(100));
        app.setYearlyInterestPercent(BigDecimal.valueOf(12));
    }

    @Test
    void computeAccountValueSingleMonth() {
        app.setMonths(1);


        BigDecimal accountValue = app.computeAccountValue();
        assertEquals(BigDecimal.valueOf(101), accountValue);
    }

    @Test
    void computeAccountValueSingleMonthWithDeductions() {
        app.setMonths(1);
        app.setMonthlyComissionPercent(BigDecimal.valueOf(0.5));

        BigDecimal accountValue = app.computeAccountValue();
        assertEquals(BigDecimal.valueOf(100.5), accountValue);
    }

    @Test
    void computeAccountValueMultipleMonths() {
        app.setMonths(3);

        BigDecimal accountValue = app.computeAccountValue();
        assertEquals(BigDecimal.valueOf(306.0401), accountValue);
    }

    @Test
    void computeAccountValueMultipleMonthsWithDeductions() {
        app.setMonths(3);
        app.setMonthlyComissionPercent(BigDecimal.valueOf(0.5));

        BigDecimal accountValue = app.computeAccountValue();
        assertEquals(BigDecimal.valueOf(303.0100125), accountValue);
    }
}