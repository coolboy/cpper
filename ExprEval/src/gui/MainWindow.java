package gui;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.*;
import parser.Calculator;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MainWindow() {
		setTitle("ExprEval - An Expression Based Calculator");
		getContentPane().setLayout(new BorderLayout());
		setDefaultCloseOperation(3);
		JPanel jpanel = new JPanel();
		getContentPane().add(jpanel);
		jpanel.setLayout(new GridLayout(2, 1, 10, 10));
		jpanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
		inputField = new JTextField(30);
		jpanel.add(inputField);
		inputField.setBorder(BorderFactory.createLoweredBevelBorder());
		inputField.setFont(new Font("Arial", 1, 24));
		inputField.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent keyevent) {
				if (keyevent.getKeyChar() == '\n')
					calButton.doClick();
			}

		});
		outputField = new JTextField(30);
		jpanel.add(outputField);
		outputField.setFont(new Font("Arial", 1, 24));
		outputField.setEnabled(false);
		JToolBar jtoolbar = new JToolBar();
		getContentPane().add(jtoolbar, "South");
		jtoolbar.setLayout(new GridLayout(1, 7, 8, 8));
		jtoolbar.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
		calButton = new JButton("Calculate");
		jtoolbar.add(calButton);
		calButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionevent) {
				try {
					Calculator calculator = new Calculator();
					double d = calculator.calculate(inputField.getText());
					String s = (new DecimalFormat("###,###,###,###.####"))
							.format(d);
					outputField.setText(s);
				} catch (Exception exception) {
					outputField.setText(exception.getMessage());
				}
			}

		});
		JButton jbutton = new JButton("Clear");
		jtoolbar.add(jbutton);
		jbutton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionevent) {
				inputField.setText("");
				outputField.setText("");
			}

		});
		JButton jbutton1 = new JButton("Cut");
		jtoolbar.add(jbutton1);
		jbutton1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionevent) {
				inputField.selectAll();
				inputField.cut();
			}

		});
		JButton jbutton2 = new JButton("Copy");
		jtoolbar.add(jbutton2);
		jbutton2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionevent) {
				outputField.setEnabled(true);
				outputField.selectAll();
				outputField.copy();
				outputField.setEnabled(false);
			}

		});
		JButton jbutton3 = new JButton("Paste");
		jtoolbar.add(jbutton3);
		jbutton3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionevent) {
				inputField.setText("");
				inputField.paste();
			}

		});
		JButton jbutton4 = new JButton("Help");
		jtoolbar.add(jbutton4);
		currentFrame = this;
		jbutton4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionevent) {
				JOptionPane.showMessageDialog(currentFrame, getHelpMessage());
			}

		});
		JButton jbutton5 = new JButton("Exit");
		jtoolbar.add(jbutton5);
		jbutton5.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionevent) {
				System.exit(0);
			}

		});
		pack();
		setVisible(true);
	}

	private String getHelpMessage() {
		String s = "";
		s = s + "ExprEval - An Expression Based Calculator, Version 1.0A\n";
		s = s
				+ "Copyright(C) 2008, Dr. Wen-jun LI (SELAB), Department of Computer Science, SUN YAT-SEN UNIVERSITY. \n";
		s = s
				+ "The experimental framework and reference solution are developed by Dr. Wen-jun LI and Da LUO. \n\n";
		s = s
				+ "The fundamental version of ExprEval covers the following features: \n";
		s = s
				+ "  1) Unsigned integral constants, for example \"058\" and \"123\". \n";
		s = s
				+ "  2) Unsigned real constants, including scientific notation: \"3.14\", \"5.1E-5\", and \"101.25E+3\". \n";
		s = s
				+ "  3) Binary arithmetic operations, for example \"1 + 2\", \"3 - 4\", \"5 * 6\", and \"7 / 8\". \n";
		s = s
				+ "  4) Only one unary arithmetic operation, for example \"-100\". \n\n";
		s = s + "This version of ExprEval also features in: \n";
		s = s + "  a) Exponential operation, for example \"5 ^ 3\". \n";
		s = s + "  b) Boolean constants: \"true\" and \"false\". \n";
		s = s
				+ "  c) Relational operations: \"=\", \"<>\", \"<\", \"<=\", \">\", and \">=\". \n";
		s = s
				+ "  d) Logical operations: \"!\" (not), \"&\" (and), and \"|\" (or). \n";
		s = s
				+ "  e) The only trinary operation, for example \"(12 * 2 > 20) ? 25 : 33\". \n";
		s = s
				+ "  f) Predefined functions \"max(x1, x2, ...)\" and \"min(x1, x2, ...)\". \n";
		s = s + "  g) Predefined functions \"sin(x)\" and \"cos(x)\". \n";
		return s;
	}

	private JTextField inputField;
	private JTextField outputField;
	private JButton calButton;
	private JFrame currentFrame;

}
