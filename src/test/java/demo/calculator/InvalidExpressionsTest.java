package demo.calculator;

import demo.calculator.Calculator;
import junit.framework.TestCase;

public class InvalidExpressionsTest extends TestCase {

	public void testInvalidOperation() {
		try {
	     Calculator obj = new Calculator();
         obj.evaluate("power(1,2)");
         fail("Expected exception for invalid operation");
		} catch (Exception ex) {
			assertTrue(ex.getMessage().contains("invalid operation"));
		}
	}
	
	public void testInvalidArguments() {
		try {
	     Calculator obj = new Calculator();
         obj.evaluate("add(2)");
         fail("Expected exception for invalid arguments");
		} catch (Exception ex) {
			assertTrue(ex.getMessage().contains("Invalid arguments"));
		}
	}
	
	public void testInvalidArguments1() {
		try {
	     Calculator obj = new Calculator();
         obj.evaluate("add()");
         fail("Expected exception for invalid arguments");
		} catch (Exception ex) {
			assertTrue(ex.getMessage().contains("Invalid arguments"));
		}
	}
	
	public void testUnbalancedParenthesis() {
		try {
	     Calculator obj = new Calculator();
         obj.evaluate("add(2,3");
         fail("Expected exception for unbalanced parenthesis");
		} catch (Exception ex) {
			assertTrue(ex.getMessage().contains("unbalanced parenthesis"));
		}
	}
	
	public void testZeroDenominatorForDiv() {
		try {
	     Calculator obj = new Calculator();
         obj.evaluate("div(2,0)");
         fail("Expected exception for zero denominator");
		} catch (Exception ex) {
			assertTrue(ex.getMessage().contains("Invalid denominator"));
		}
	}	
}
