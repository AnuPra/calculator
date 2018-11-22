package demo.calculator;

import demo.calculator.Calculator;
import demo.calculator.InvalidExpressionException;
import junit.framework.TestCase;

public class AdditionalExamplesTest extends TestCase{
	
	public void testExample1() throws InvalidExpressionException {
		Calculator obj = new Calculator();
		assertEquals(obj.evaluate("let(b,let(b,5,add(b,5)),add(b,5))"),"15");    
	}
	
	public void testExample2() throws InvalidExpressionException {
		Calculator obj = new Calculator();
		assertEquals(obj.evaluate("let(a,let(b,add(mult(10,2),mult(5,3)),add(b,10)),add(a,20))"),"65");
    }

	public void testExample3() throws InvalidExpressionException {
		Calculator obj = new Calculator();
	    assertEquals(obj.evaluate("let(a,let(b,let(a,let(b,add(10,5),add(b,3)),add(a,5)),add(b,3)),add(a,6))"),"32");
	}
	
}
