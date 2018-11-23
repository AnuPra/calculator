package demo.calculator;

import demo.calculator.Calculator;
import junit.framework.TestCase;

public class CalculatorTest extends TestCase{
	
	public void testAdd() {
		Calculator.main(new String[] {"add(1,2)"});
	}
	
	public void testMissingArgument() {
		Calculator.main(new String[] {});
	}
	
	public void testEmptyArgument() {
		Calculator.main(new String[] {" "});
	}	
	
	public void testInvalidExpressionException() {
		Calculator.main(new String[] {"add(1,2"});
	}	
}
