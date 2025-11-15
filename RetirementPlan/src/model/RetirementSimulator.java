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
	 
	    // Step 1: Input validation (using if statements)
	    if (principal < 0) {
	        throw new IllegalArgumentException("Principal must be non-negative.");
	    }
	    if (years < 0) {
	        throw new IllegalArgumentException("Years must be non-negative.");
	    }
	    if (rate <= -1.0) {
	        throw new IllegalArgumentException("Rate cannot be less than -100%.");
	    }

	    
	    //double futureValue = principal * Math.pow(1.0 + rate, years);

	    // Step 2: Initialize result variable
	    double futureValue = principal;

	    // Step 3: Simulate compounding using a simple loop (O(n))
	    int currentYear = 0;  // loop counter
	    while (currentYear < years) {
	        // increase balance by rate each year
	        futureValue = futureValue * (1 + rate);

	        // move to next year
	        currentYear++;
	    }

	    // Step 4: Return the result after all years
	    return futureValue;
	}
}


/* Stimulates the years a retirement fund would last while withdrawing a fixed amount each year and applying annual interest rate.
	@param startingBalance intial amount in the retirement amount.
	@param annualWithdrawl the fixed withdrawl for each year.
	@param interestRate the annual growth interest rate. 
	@return the number of years before the account is depleted. 
*/
// Methods notation O(n)

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

	double currentBalance = startinBalance;
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
		currentBalance = postWithdrawl;

		duration++; // years count 

		// Stop progream once money finish
		if (currentBalance <=0){
			break;
		}
	}
	return duration;
}

		






