package gui;
//Group Members: Bradd Codner, Leigh-Ann Cammock, Josan Williams, Rianna Shone.
//AOA Final programming assignment.
// File: RetirementSimulatorGUI.java

import javax.swing.*;

//Imports The class containing the implemented functions
import model.RetirementSimulator;

import java.awt.*;

/*
 * Presentation layer for the Retirement Simulator application.
 */
//GUI builder 
public class RetirementSimulatorGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    // Function 1 Default Inputs – Fixed Growth (O(n))
    private final JTextField fixedPrincipalField = new JTextField("10000", 8);
    private final JTextField fixedRateField = new JTextField("7.5", 5);
    private final JTextField fixedYearsField = new JTextField("30", 4);
    private final JLabel fixedResultLabel = new JLabel("Fixed: (not calculated)");

    // Function 2 Default Inputs – Variable Growth (O(n))
    // Interpretation:
    // - Principal: variablePrincipalField
    // - Base rate comes from fixedRateField (%)
    // - Rate increment per year: variableRateField (%/year)
    // - Years: variableYearsField
    private final JTextField variablePrincipalField = new JTextField("10000", 8);
    private final JTextField variableRateField = new JTextField("0.5", 5); // increment %/yr
    private final JTextField variableYearsField = new JTextField("30", 4);
    private final JLabel variableResultLabel = new JLabel("Variable: (not calculated)");

    // Function 3 Default Inputs – Depletion (O(n))
    private final JTextField depletionBalanceField = new JTextField("500000", 8);
    private final JTextField depletionWithdrawField = new JTextField("40000", 8);
    private final JTextField depletionRateField = new JTextField("5.0", 5);
    private final JLabel depletionResultLabel = new JLabel("Depletion: (not calculated)");

    // Function 4 Default Inputs – Optimization (O(log n))
    // Here we interpret optBalanceField as the starting balance
    private final JTextField optBalanceField = new JTextField("500000", 8);
    private final JTextField optRateField = new JTextField("7.5", 5);
    private final JTextField optYearsField = new JTextField("30", 4);
    private final JLabel optimizationResultLabel = new JLabel("Optimization: (not calculated)");

	// Constructor to set up the GUI
    public RetirementSimulatorGUI() {
        super("Retirement Simulator - Minimal GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 6, 6));
        mainPanel.add(createFixedPanel());
        mainPanel.add(createVariablePanel());
        mainPanel.add(createDepletionPanel());
        mainPanel.add(createOptimizationPanel());

        add(mainPanel, BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

	
	//Fixed Investor Panel being genereated
    private JPanel createFixedPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Function 1: Fixed Growth (O(n))"));
        panel.add(new JLabel("Principal $:"));
        panel.add(fixedPrincipalField);
        panel.add(new JLabel("Rate %:"));
        panel.add(fixedRateField);
        panel.add(new JLabel("Years:"));
        panel.add(fixedYearsField);
        JButton calcButton = new JButton("Calculate Fixed");
        calcButton.addActionListener(e -> onCalculateFixed());
        panel.add(calcButton);
        panel.add(fixedResultLabel);
        return panel;
    }

	//Variable Investor Panel being genereated
    private JPanel createVariablePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Function 2: Variable Growth (O(n))"));
        panel.add(new JLabel("Principal $:"));
        panel.add(variablePrincipalField);
        panel.add(new JLabel("Rate Inc %/yr:"));
        panel.add(variableRateField);
        panel.add(new JLabel("Years:"));
        panel.add(variableYearsField);
        JButton btn = new JButton("Calculate Variable");
        btn.addActionListener(e -> onCalculateVariable());//button used to simulate function call
        panel.add(btn);
        panel.add(variableResultLabel);
        return panel;
    }

	
	//Finally Retired Panel being genereated
    private JPanel createDepletionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Function 3: Depletion (O(n))"));
        panel.add(new JLabel("Balance $:"));
        panel.add(depletionBalanceField);
        panel.add(new JLabel("Withdrawal $:"));
        panel.add(depletionWithdrawField);
        panel.add(new JLabel("Rate %:"));
        panel.add(depletionRateField);
        JButton btn = new JButton("Calculate Depletion");
        btn.addActionListener(e -> onCalculateDepletion());//button used to simulate function call
        panel.add(btn);
        panel.add(depletionResultLabel);
        return panel;
    }

	//Maximum Expensed Panel being genereated
    private JPanel createOptimizationPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Function 4: Optimization (O(log n))"));
        panel.add(new JLabel("Balance $:"));
        panel.add(optBalanceField);
        panel.add(new JLabel("Rate %:"));
        panel.add(optRateField);
        panel.add(new JLabel("Years:"));
        panel.add(optYearsField);
        JButton btn = new JButton("Calculate Optimization");
        btn.addActionListener(e -> onCalculateOptimization());//button used to simulate function call
        panel.add(btn);
        panel.add(optimizationResultLabel);
        return panel;
    }


	// Control Panel with Calculate All and Close buttons 
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton calculateAll = new JButton("Calculate All");
        calculateAll.addActionListener(e -> {		//button used to simulate function call
            onCalculateFixed();
            onCalculateVariable();
            onCalculateDepletion();
            onCalculateOptimization();
        });
        JButton close = new JButton("Close");
        close.addActionListener(e -> dispose());//closes the GUI
        panel.add(calculateAll);
        panel.add(close);
        return panel;
    }

    // =========================
    // Event Handlers / Actions
    // =========================

	//Fixed Growth Calculation
    private void onCalculateFixed() {
		//Input validation
        try {
            double principal = Double.parseDouble(fixedPrincipalField.getText().trim());
            double rate = Double.parseDouble(fixedRateField.getText().trim()) / 100.0;
            int years = Integer.parseInt(fixedYearsField.getText().trim());
            double result = RetirementSimulator.fixedInvestor(principal, rate, years);
            fixedResultLabel.setText(String.format("Fixed: $%,.2f", result));
        } catch (NumberFormatException e) {
            fixedResultLabel.setText("Invalid input: numbers only");
        } catch (IllegalArgumentException | ArithmeticException e) {
            fixedResultLabel.setText("Error: " + e.getMessage());
        }
    }

	//Variable Growth Calculation
    private void onCalculateVariable() {
        try {
            double principal = Double.parseDouble(variablePrincipalField.getText().trim());
            double baseRate = Double.parseDouble(fixedRateField.getText().trim()) / 100.0;
            double increment = Double.parseDouble(variableRateField.getText().trim()) / 100.0;
            int years = Integer.parseInt(variableYearsField.getText().trim());

            if (years <= 0) {
                throw new IllegalArgumentException("Years must be positive.");
            }

            // Build the per-year rates list: baseRate, baseRate+inc, baseRate+2*inc, ...
            double[] ratesList = new double[years];
            for (int i = 0; i < years; i++) {
                ratesList[i] = baseRate + (i * increment);
            }

            double result = RetirementSimulator.variableInvestor(principal, ratesList);
            variableResultLabel.setText(String.format("Variable: $%,.2f", result));
        } catch (NumberFormatException e) {
            variableResultLabel.setText("Invalid input: numbers only");
        } catch (IllegalArgumentException | ArithmeticException e) {
            variableResultLabel.setText("Error: " + e.getMessage());
        }
    }

	// Finally Retired Calculation
    private void onCalculateDepletion() {
        try {
            double startingBalance = Double.parseDouble(depletionBalanceField.getText().trim());
            double annualWithdrawal = Double.parseDouble(depletionWithdrawField.getText().trim());
            double rate = Double.parseDouble(depletionRateField.getText().trim()) / 100.0;

            int years = RetirementSimulator.finallyRetired(startingBalance, annualWithdrawal, rate);

            depletionResultLabel.setText("Depletion: " + years + " years");
        } catch (NumberFormatException e) {
            depletionResultLabel.setText("Invalid input: numbers only");
        } catch (IllegalArgumentException | ArithmeticException e) {
            depletionResultLabel.setText("Error: " + e.getMessage());
        }
    }

	//Maximum Expensed Calculation
    private void onCalculateOptimization() {
        try {
            double principal = Double.parseDouble(optBalanceField.getText().trim());
            double rate = Double.parseDouble(optRateField.getText().trim()) / 100.0;
            int years = Integer.parseInt(optYearsField.getText().trim());

            double withdrawal = RetirementSimulator.maximumExpensed(principal, rate, years);
            optimizationResultLabel.setText(String.format("Optimization: $%,.2f per year", withdrawal));
        } catch (NumberFormatException e) {
            optimizationResultLabel.setText("Invalid input: numbers only");
        } catch (IllegalArgumentException | ArithmeticException e) {
            optimizationResultLabel.setText("Error: " + e.getMessage());
        }
    }

	//Uses invoke later method to call the GUI function
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RetirementSimulatorGUI().setVisible(true));
    }
}
