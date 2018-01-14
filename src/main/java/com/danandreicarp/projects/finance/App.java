package com.danandreicarp.projects.finance;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.BigDecimalConverter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author <a href="mailto:dan.andrei.carp@gmail.com">Dan Andrei Carp</a>
 */
@Parameters
public class App {

    private static final BigDecimal MONTHS_IN_YEAR = BigDecimal.valueOf(12);

    @Parameter(names = {"-h", "--help"}, description = "Print the help screen and exit the app.", help = true)
    private boolean cmdHelp = false;

    @Parameter(names = "--monthlyContribution", required = true)
    private BigDecimal monthlyContribution;

    @Parameter(names = "--yearlyInterestPercent", converter = BigDecimalConverter.class, required = true)
    private BigDecimal yearlyInterestPercent;

    @Parameter(names = "--monthlyComissionPercent", converter = BigDecimalConverter.class, required = false)
    private BigDecimal monthlyComissionPercent;

    @Parameter(names = "--months", description = "over how many months to compute the account value",
            required = true)
    private long months;


    public void setMonthlyContribution(BigDecimal monthlyContribution) {
        this.monthlyContribution = monthlyContribution;
    }

    public void setYearlyInterestPercent(BigDecimal yearlyInterestPercent) {
        this.yearlyInterestPercent = yearlyInterestPercent;
    }

    public void setMonthlyComissionPercent(BigDecimal monthlyComissionPercent) {
        this.monthlyComissionPercent = monthlyComissionPercent;
    }

    public void setMonths(long months) {
        this.months = months;
    }

    public static void main(String[] args) {

        App app = new App();

        if (app.initialize(args)) {
            BigDecimal accountValue = app.computeAccountValue();
            System.out.println(accountValue);
        }
    }

    BigDecimal computeAccountValue() {

        BigDecimal valueSoFar = BigDecimal.ZERO;
        BigDecimal monthlyInterestPercent = yearlyInterestPercent.divide(MONTHS_IN_YEAR, MathContext.DECIMAL64);
        for (int i = 0; i < months; i++) {
            valueSoFar = valueSoFar.add(monthlyContribution);
            BigDecimal monthlyDeductions = BigDecimal.ZERO;
            if (monthlyComissionPercent != null) {
                monthlyDeductions = valueSoFar.multiply(monthlyComissionPercent).divide(BigDecimal.valueOf(100), MathContext.DECIMAL64);
            }
            BigDecimal monthlyInterest = valueSoFar.multiply(monthlyInterestPercent).divide(BigDecimal.valueOf(100), MathContext.DECIMAL64);
            valueSoFar = valueSoFar.subtract(monthlyDeductions).add(monthlyInterest);
        }

        return valueSoFar;
    }

    private boolean initialize(String[] args) {
        JCommander jCommander = parseCommandLineArguments(args);

        if (cmdHelp) {
            jCommander.usage();
            return false;
        }

        return true;
    }

    private JCommander parseCommandLineArguments(String[] args) {
        JCommander jCommander = new JCommander(this);
        jCommander.setProgramName(this.getClass().getCanonicalName());
        jCommander.setAcceptUnknownOptions(true);
        jCommander.parse(args);

        return jCommander;
    }

}
