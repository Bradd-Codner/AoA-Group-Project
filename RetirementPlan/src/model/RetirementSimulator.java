package model;

//File: RetirementSimulator.java
/**
* Logic layer for retirement savings computations.
* Contains only mathematical/business logic methods.
*/
public final class RetirementSimulator {

 private RetirementSimulator() {
     // Prevent instantiation
 }

 /**
  * Calculates the future value of a fixed-rate investment using the
  * O(1) closed-form compound interest formula.
  *
  * @param principal Initial investment (must be >= 0)
  * @param rate Annual interest rate as decimal (e.g., 0.075 for 7.5%)
  * @param years Number of years (must be >= 0)
  * @return Future value of investment
  * @throws IllegalArgumentException if inputs are invalid
  */
 public static double fixedInvestor(double principal, double rate, int years) {
     if (Double.isNaN(principal) || Double.isNaN(rate))
         throw new IllegalArgumentException("Inputs must be numeric.");
     if (Double.isInfinite(principal) || Double.isInfinite(rate))
         throw new IllegalArgumentException("Inputs must be finite.");
     if (principal < 0)
         throw new IllegalArgumentException("Principal must be non-negative.");
     if (years < 0)
         throw new IllegalArgumentException("Years must be non-negative.");
     if (rate <= -1.0)
         throw new IllegalArgumentException("Rate cannot be less than -100%.");

     double futureValue = principal * Math.pow(1.0 + rate, years);

     if (Double.isInfinite(futureValue) || Double.isNaN(futureValue))
         throw new ArithmeticException("Computation overflow or invalid result.");

     return futureValue;
 }
}

