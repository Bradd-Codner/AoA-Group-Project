// Project: Retirement Simulator
// Package: model

package model;

import java.util.function.DoubleUnaryOperator;

/**
 * Logic layer for retirement savings computations.
 * Contains only mathematical/business logic methods.
 */
public class RetirementSimulator {

    /**
     * Calculates the future value of a fixed-rate investment using
     * year-by-year compounding (O(n)).
     *
     * @param principal Initial investment (must be >= 0)
     * @param rate      Annual interest rate as decimal (e.g., 0.075 for 7.5%)
     * @param years     Number of years (must be >= 0)
     * @return Future value of investment
     * @throws IllegalArgumentException if inputs are invalid
     */
    public static double fixedInvestor(double principal, double rate, int years) {
        // Validation
        if (principal < 0) {
            throw new IllegalArgumentException("Principal must be non-negative.");
        }
        if (years < 0) {
            throw new IllegalArgumentException("Years must be non-negative.");
        }
        if (rate <= -1.0) {
            throw new IllegalArgumentException("Rate cannot be less than -100%.");
        }

        // Year-by-year compounding
        double futureValue = principal;
        int currentYear = 0;

		//loops to a specified number of years while increasing the principal by interest every year
        while (currentYear < years) {
            futureValue = futureValue * (1 + rate);
            currentYear++;
        }
		//return futureValue as the expected value after fixed investment
        return futureValue;
    }

    /**
     * Calculates the future value of an investment with variable annual rates.
     *
     * @param principal Initial investment (must be >= 0)
     * @param ratesList Array of annual interest rates as decimals
     * @return Future value of investment
     * @throws IllegalArgumentException if inputs are invalid
     */
    public static double variableInvestor(double principal, double[] ratesList) {
        if (principal < 0) {
            throw new IllegalArgumentException("Principal must be non-negative.");
        }
        if (ratesList == null || ratesList.length == 0) {
            throw new IllegalArgumentException("Rates list cannot be null or empty.");
        }

        double futureValue = principal;

        for (double rate : ratesList) {
            if (rate <= -1.0) {
                throw new IllegalArgumentException("Rate cannot be less than -100%.");
            }
            if (Double.isNaN(rate) || Double.isInfinite(rate)) {
                throw new IllegalArgumentException("Rates must be finite numbers.");
            }
            // Allow negative rates (loss) but validate the number itself
            futureValue *= (1 + rate);
        }

        return futureValue;
    }

    /**
     * Simulates how many years a retirement fund will last while withdrawing a
     * fixed amount each year and applying an annual interest rate.
     *
     * Time complexity: O(n), where n is the number of years until depletion.
     *
     * @param startingBalance  Initial amount in the retirement account (>= 0)
     * @param annualWithdrawal Fixed withdrawal amount each year (> 0)
     * @param interestRate     Annual growth rate as decimal (e.g., 0.05 for 5%)
     * @return Number of years before the account is depleted
     * @throws IllegalArgumentException if inputs are invalid
     */
    public static int finallyRetired(double startingBalance,double annualWithdrawal, double interestRate){

        // Validation of Input
        if ( startingBalance <0){
            throw new IllegalArgumentException("Balance must not be a negative ");
        }
        if(annualWithdrawal<=0){
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero(0)");
        }
        if (interestRate <= -1.0)
        {
            throw new IllegalArgumentException("Grown rate cannot be less than -100%");
        }

        //stimulation variables 

        double currentBalance = startingBalance;
        int duration =0;

        double interestEarned; // interest gained this year
        double postInterestBalance;// balance after interest
        double postWithdrawal; // balance after annual withdrawl

        // Year by year stimulation 
        while (currentBalance > 0){
            //Calculates the interest for year 
            interestEarned = currentBalance * interestRate;

            //add interest to balance 
            postInterestBalance = currentBalance + interestEarned;

            // withdrawl amount subtract
            postWithdrawal = postInterestBalance -annualWithdrawal ;

            // Update current Balance 
            currentBalance = postWithdrawal;

            duration++; // years count 

            // Stop progream once money finish
            if (currentBalance <=0){
                break;
            }
        }
        return duration;
    }

    /**
     * Overload for maximumExpensed with default retirement period of 30 years.
     */
    public static double maximumExpensed(double principal, double rate) {
        return maximumExpensed(principal, rate, 30);
    }

    /**
     * Uses binary search to find the maximum constant annual withdrawal such that
     * after `years` of:
     *      B <- B * (1 + rate) - withdrawal
     * the final balance is approximately zero.
     *
     * Time complexity: O(years * log(range / tolerance)).
     *
     * @param principal Starting retirement balance (>= 0)
     * @param rate      Annual growth rate as decimal (e.g., 0.05 for 5%)
     * @param years     Modeled retirement period (e.g., 30)
     * @return Optimal annual withdrawal rounded to cents
     */

	//changed to principal to match 
    public static double maximumExpensed(double principal, double rate, int years) {
        if (Double.isNaN(principal) || Double.isNaN(rate)) {
            throw new IllegalArgumentException("Inputs must be numeric.");
        }
        if (Double.isInfinite(principal) || Double.isInfinite(rate)) {
            throw new IllegalArgumentException("Inputs must be finite.");
        }
        if (principal <= 0.0) {
            return 0.0; // nothing to withdraw
        }
        if (years <= 0) {
            throw new IllegalArgumentException("Years must be positive.");
        }
		//added rate validation
        if (rate <= -1.0) {
            throw new IllegalArgumentException("Rate cannot be less than -100%.");
        }

        final double TOL = 0.01;      // ~$0.01 tolerance on ending balance
        final int MAX_ITERS = 200;    // bisection steps

        // Helper: ending balance after `years` if we withdraw W each year.
        DoubleUnaryOperator endBalance = (withdrawal) -> {
            double b = principal;
            for (int i = 0; i < years; i++) {
                b = b * (1.0 + rate) - withdrawal;
            }
            return b;
        };

        double lo = 0.0;
        double hi = (rate <= 0.0)
                ? principal
                : principal * Math.pow(1.0 + rate, years);

        // Ensure hi is large enough to deplete the balance
        while (endBalance.applyAsDouble(hi) > 0.0 && hi < 1e18) {
            hi *= 2.0;
        }

        // Standard bisection (binary search)
        for (int it = 0; it < MAX_ITERS; it++) {
            double mid = 0.5 * (lo + hi);
            double eb  = endBalance.applyAsDouble(mid);

            if (Math.abs(eb) <= TOL) {
                return roundToCents(mid);
            }
            if (eb > 0.0) {
                lo = mid;   // withdrawing too little → increase withdrawal
            } else {
                hi = mid;   // withdrawing too much → decrease withdrawal
            }
        }

        return roundToCents(0.5 * (lo + hi));
    }

    // Helper: round a monetary value to two decimal places (cents)
    private static double roundToCents(double x) {
        return Math.round(x * 100.0) / 100.0;
    }
}
