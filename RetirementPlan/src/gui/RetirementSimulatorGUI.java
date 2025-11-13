package gui;

//File: RetirementSimulatorGUI.java


import javax.swing.*;

import model.RetirementSimulator;

import java.awt.*;

/**
* Presentation layer for the Retirement Simulator application.
* Includes placeholders for all four functions and the Map API.
*/
public class RetirementSimulatorGUI extends JFrame {

 /**
	 * 
	 */
 private static final long serialVersionUID = 1L;
 
// Function 1 Inputs
 private final JTextField fixedPrincipalField = new JTextField("10000", 8);
 private final JTextField fixedRateField = new JTextField("7.5", 5);
 private final JTextField fixedYearsField = new JTextField("30", 4);
 private final JLabel fixedResultLabel = new JLabel("Fixed: (not calculated)");

 // Function 2 Inputs
 private final JTextField variablePrincipalField = new JTextField("10000", 8);
 private final JTextField variableRateField = new JTextField("0.5", 5);
 private final JTextField variableYearsField = new JTextField("30", 4);
 private final JLabel variableResultLabel = new JLabel("Variable: (placeholder)");

 // Function 3 Inputs
 private final JTextField depletionBalanceField = new JTextField("500000", 8);
 private final JTextField depletionWithdrawField = new JTextField("40000", 8);
 private final JTextField depletionRateField = new JTextField("5.0", 5);
 private final JLabel depletionResultLabel = new JLabel("Depletion: (placeholder)");

 // Function 4 Inputs
 private final JTextField optTargetField = new JTextField("100000", 8);
 private final JTextField optRateField = new JTextField("7.5", 5);
 private final JTextField optYearsField = new JTextField("30", 4);
 private final JTextField optMinField = new JTextField("1000", 6);
 private final JTextField optMaxField = new JTextField("50000", 6);
 private final JLabel optimizationResultLabel = new JLabel("Optimization: (placeholder)");

 // Map API Placeholder (Mandatory Feature)
// private final JPanel mapPlaceholderPanel = new JPanel();

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

 private JPanel createFixedPanel() {
     JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
     panel.setBorder(BorderFactory.createTitledBorder("Function 1: Fixed Growth (O(1))"));
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
     btn.addActionListener(e -> variableResultLabel.setText("Variable: Placeholder result (O(n))"));
     panel.add(btn);
     panel.add(variableResultLabel);
     return panel;
 }

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
     btn.addActionListener(e -> depletionResultLabel.setText("Depletion: Placeholder result (O(n))"));
     panel.add(btn);
     panel.add(depletionResultLabel);
     return panel;
 }

 private JPanel createOptimizationPanel() {
     JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
     panel.setBorder(BorderFactory.createTitledBorder("Function 4: Optimization (O(log n))"));
     panel.add(new JLabel("Target $:"));
     panel.add(optTargetField);
     panel.add(new JLabel("Rate %:"));
     panel.add(optRateField);
     panel.add(new JLabel("Years:"));
     panel.add(optYearsField);
     panel.add(new JLabel("Min $:"));
     panel.add(optMinField);
     panel.add(new JLabel("Max $:"));
     panel.add(optMaxField);
     JButton btn = new JButton("Calculate Optimization");
     btn.addActionListener(e -> optimizationResultLabel.setText("Optimization: Placeholder result (O(log n))"));
     panel.add(btn);
     panel.add(optimizationResultLabel);
     return panel;
 }

 /*private JPanel createMapPanel() {
     JPanel panel = new JPanel(new BorderLayout());
     panel.setBorder(BorderFactory.createTitledBorder("Map API Placeholder (Mandatory Feature)"));
     mapPlaceholderPanel.setPreferredSize(new Dimension(100, 50));
     mapPlaceholderPanel.setBackground(Color.LIGHT_GRAY);
     panel.add(mapPlaceholderPanel, BorderLayout.CENTER);
     JLabel hint = new JLabel("Map placeholder — integrate API here", SwingConstants.CENTER);
     panel.add(hint, BorderLayout.SOUTH);
     return panel;
 }*/

 private JPanel createControlPanel() {
     JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
     JButton calculateAll = new JButton("Calculate All");
     calculateAll.addActionListener(e -> onCalculateFixed());
     JButton close = new JButton("Close");
     close.addActionListener(e -> dispose());
     panel.add(calculateAll);
     panel.add(close);
     return panel;
 }

 private void onCalculateFixed() {
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

 public static void main(String[] args) {
     SwingUtilities.invokeLater(() -> new RetirementSimulatorGUI().setVisible(true));
 }
}
