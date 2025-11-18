package maxexpensed;

public class Finance {

    // Overload for the 2-parameter signature in the brief; defaults to 30 years.
    public static double maximumExpensed(double balance, double rate) {
        return maximumExpensed(balance, rate, 30);
    }

    /**
     * Binary-search the maximum constant annual withdrawal E such that after `years`
     * of:  B <- B*(1 + rate) - E, the final balance is approximately zero.
     *
     * @param balance starting retirement balance (>= 0)
     * @param rate    annual growth rate as a decimal (e.g., 0.05 for 5%)
     * @param years   modeled retirement period (e.g., 30)
     * @return        optimal annual withdrawal rounded to cents
     */
    public static double maximumExpensed(double balance, double rate, int years) {
        if (balance <= 0 || years <= 0) return 0.0;

        final double TOL = 0.01;        // ~$0.01 tolerance on ending balance
        final int MAX_ITERS = 200;      // bisection steps

        // Helper: ending balance after `years` if we withdraw E each year.
        java.util.function.DoubleUnaryOperator endBalance = (E) -> {
            double b = balance;
            for (int i = 0; i < years; i++) {
                b = b * (1.0 + rate) - E;
            }
            return b;
        };

        // Bracket the root: find hi so that endBalance(hi) <= 0 (i.e., we withdrew too much).
        double lo = 0.0;
        double hi = (rate <= 0.0) ? balance : balance * Math.pow(1.0 + rate, years);
        while (endBalance.applyAsDouble(hi) > 0.0 && hi < 1e18) {
            hi *= 2.0; // expand until we force depletion within the horizon
        }

        // Standard bisection
        for (int it = 0; it < MAX_ITERS; it++) {
            double mid = 0.5 * (lo + hi);
            double eb  = endBalance.applyAsDouble(mid);

            if (Math.abs(eb) <= TOL) {
                return roundToCents(mid);
            }
            if (eb > 0.0) {
                // Withdrawing too little (money left over) -> raise E
                lo = mid;
            } else {
                // Withdrawing too much (negative before horizon) -> lower E
                hi = mid;
            }
        }

        return roundToCents(0.5 * (lo + hi));
    }

    private static double roundToCents(double x) {
        return Math.round(x * 100.0) / 100.0;
    }
}