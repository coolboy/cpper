// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExprEvalTest.java

package test;

import exceptions.*;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import parser.Calculator;

// Referenced classes of package test:
//            TestCase

public class ExprEvalTest {
	public static void main(String args[]) throws Exception {
		total = warnings = errors = 0;
		fileName = "../testcases/standard.xml";
		if (args.length >= 1)
			fileName = args[0];
		System.out.println("Testing Procedure");
		System.out
				.println("========================================================");
		Document document = getDoc();
		test(document);
		System.out
				.println("--------------------------------------------------------");
		System.out.println("Statistics Report (" + total + " test cases):\n");
		if (total == 0) {
			return;
		} else {
			System.out.println("\tPassed case(s): "
					+ (total - warnings - errors) + " ("
					+ ((double) (total - warnings - errors) * 100D)
					/ (double) total + "%)");
			System.out.println("\tWarning case(s): " + warnings + " ("
					+ ((double) warnings * 100D) / (double) total + "%)");
			System.out.println("\tFailed case(s): " + errors + " ("
					+ ((double) errors * 100D) / (double) total + "%)");
			System.out
					.println("========================================================\n");
			return;
		}
	}

	public static Document getDoc() throws SAXException {
		Document document = null;
		try {
			document = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(fileName);
		} catch (ParserConfigurationException parserconfigurationexception) {
			System.out.println("XML parser configuration failed: "
					+ parserconfigurationexception);
			System.exit(1);
		} catch (FileNotFoundException filenotfoundexception) {
			System.out.println("XML file not found: " + fileName);
			System.exit(1);
		} catch (DOMException domexception) {
			System.out.println("XML parser finds an error in the document: "
					+ domexception);
			System.exit(1);
		} catch (Exception exception) {
			System.out.println("XML parser encounters an error: " + exception);
			System.exit(1);
		}
		return document;
	}

	public static void test(Document document) throws ExpressionException {
		Element element = document.getDocumentElement();
		element.normalize();
		NodeList nodelist = element.getElementsByTagName("test-case");
		for (int i = 0; i < nodelist.getLength(); i++) {
			Element element1 = (Element) nodelist.item(i);
			NodeList nodelist1 = element1.getElementsByTagName("id");
			if (nodelist1.getLength() != 1) {
				System.out
						.println("Test case format error in XML document: each test case must have one and only one ID. ");
				continue;
			}
			String s = ((Text) ((Element) nodelist1.item(0)).getFirstChild())
					.getNodeValue();
			NodeList nodelist2 = element1.getElementsByTagName("description");
			if (nodelist2.getLength() != 1) {
				System.out
						.println("Test case format error in XML document: each test case must have one and only one description. ");
				continue;
			}
			String s1 = ((Text) ((Element) nodelist2.item(0)).getFirstChild())
					.getNodeValue();
			NodeList nodelist3 = element1.getElementsByTagName("input");
			if (nodelist3.getLength() != 1) {
				System.out
						.println("Test case format error in XML document: each test case must have one and only one input. ");
				continue;
			}
			String s2 = ((Text) ((Element) nodelist3.item(0)).getFirstChild())
					.getNodeValue();
			String s3 = "";
			boolean flag = false;
			NodeList nodelist4 = element1.getElementsByTagName("output");
			if (nodelist4.getLength() > 1) {
				System.out
						.println("Test case format error in XML document: each test case must have no more than one output. ");
				continue;
			}
			if (nodelist4.getLength() == 1) {
				s3 = ((Text) ((Element) nodelist4.item(0)).getFirstChild())
						.getNodeValue();
			} else {
				NodeList nodelist5 = element1.getElementsByTagName("exception");
				if (nodelist5.getLength() > 1) {
					System.out
							.println("Test case format error in XML document: each test case must have no more than one exception. ");
					continue;
				}
				if (nodelist5.getLength() == 1) {
					s3 = ((Text) ((Element) nodelist5.item(0)).getFirstChild())
							.getNodeValue();
				} else {
					System.out
							.println("Test case format error in XML document: each test case must have one expected output or exception. ");
					continue;
				}
			}
			test(new TestCase(s, s1, s2, s3, flag));
		}

		System.out.println("");
	}

	public static void test(TestCase testcase) throws ExpressionException {
		String s = "###,###,###,###.####";
		String s1 = "";
		try {
			double d = (new Calculator()).calculate(testcase.getInput());
			try {
				String s2 = (new DecimalFormat(s)).format(d);
				String s3 = (new DecimalFormat(s)).format(Double
						.parseDouble(testcase.getOutput()));
				if (!s2.equals(s3)) {
					errors++;
					s1 = "Error: evaluation result is not expected (output = "
							+ s2 + ", expected = " + s3 + "). ";
				}
			} catch (Exception exception1) {
				errors++;
				s1 = "Error: evaluation result is not a double type. ";
			}
		} catch (ExpressionException expressionexception) {
			if (testcase.isException()) {
				errors++;
				s1 = "Error: wrong detection for a correct input ("
						+ expressionexception + "). ";
			}
			if ((!(expressionexception instanceof DividedByZeroException) || !testcase
					.getOutput().equalsIgnoreCase("DividedByZeroException"))
					&& (!(expressionexception instanceof EmptyExpressionException) || !testcase
							.getOutput().equalsIgnoreCase(
									"EmptyExpressionException"))
					&& (!(expressionexception instanceof ExpressionException) || !testcase
							.getOutput()
							.equalsIgnoreCase("ExpressionException"))
					&& (!(expressionexception instanceof FunctionCallException) || !testcase
							.getOutput().equalsIgnoreCase(
									"FunctionCallException"))
					&& (!(expressionexception instanceof IllegalDecimalException) || !testcase
							.getOutput().equalsIgnoreCase(
									"IllegalDecimalException"))
					&& (!(expressionexception instanceof IllegalIdentifierException) || !testcase
							.getOutput().equalsIgnoreCase(
									"IllegalIdentifierException"))
					&& (!(expressionexception instanceof IllegalSymbolException) || !testcase
							.getOutput().equalsIgnoreCase(
									"IllegalSymbolException"))
					&& (!(expressionexception instanceof LexicalException) || !testcase
							.getOutput().equalsIgnoreCase("LexicalException"))
					&& (!(expressionexception instanceof MissingLeftParenthesisException) || !testcase
							.getOutput().equalsIgnoreCase(
									"MissingLeftParenthesisException"))
					&& (!(expressionexception instanceof MissingOperandException) || !testcase
							.getOutput().equalsIgnoreCase(
									"MissingOperandException"))
					&& (!(expressionexception instanceof MissingOperatorException) || !testcase
							.getOutput().equalsIgnoreCase(
									"MissingOperatorException"))
					&& (!(expressionexception instanceof MissingRightParenthesisException) || !testcase
							.getOutput().equalsIgnoreCase(
									"MissingRightParenthesisException"))
					&& (!(expressionexception instanceof SemanticException) || !testcase
							.getOutput().equalsIgnoreCase("SemanticException"))
					&& (!(expressionexception instanceof SyntacticException) || !testcase
							.getOutput().equalsIgnoreCase("SyntacticException"))
					&& (!(expressionexception instanceof TrinaryOperationException) || !testcase
							.getOutput().equalsIgnoreCase(
									"TrinaryOperationException"))
					&& (!(expressionexception instanceof TypeMismatchedException) || !testcase
							.getOutput().equalsIgnoreCase(
									"TypeMismatchedException"))) {
				warnings++;
				s1 = "Warning: exception is not an expected type ("
						+ expressionexception + "). ";
			}
		} catch (Exception exception) {
			errors++;
			s1 = "Error: exception found in your program (" + exception + "). ";
		} finally {
			total++;
			if (s1.equals("")) {
				System.out.println("\n" + testcase + "\nPassed !");
			} else {
				System.out.println("\n" + testcase);
				System.out.println(s1);
			}
		}
	}

	public static String fileName;
	public static int total;
	public static int warnings;
	public static int errors;
}
