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
	
	public void testAddWithDouble() throws InvalidExpressionException {
		Calculator obj = new Calculator();
	    assertEquals(obj.evaluate("add(1.2,3.4)"),"4.6");
	}
	
	public void testSubWithDouble() throws InvalidExpressionException {
		Calculator obj = new Calculator();
	    assertEquals(obj.evaluate("sub(1.2,3.4)"),"2.2");
	}
	
	public void testMultWithDouble() throws InvalidExpressionException {
		Calculator obj = new Calculator();
	    assertEquals(obj.evaluate("mult(1.2,3.4)"),"4.08");
	}
	
	public void testDivWithDouble() throws InvalidExpressionException {
		Calculator obj = new Calculator();
	    assertEquals(obj.evaluate("div(1.2,3.4)"),"0.35294117647058826");
	}
	
   public void testDuplicateVariablesInScope() throws InvalidExpressionException {
	    Calculator obj = new Calculator();
	    assertEquals(obj.evaluate("let(a,5,let(b,let(a,10,add(a,5)),mult(b,a)))"),"75");
	}
   
   public void testExample4() throws InvalidExpressionException {
	    Calculator obj = new Calculator();
	    assertEquals(obj.evaluate("let(a,5,let(b,let(a,add(5,10),add(a,5)),mult(b,a)))"),"100");
	}

   public void testExample5() throws InvalidExpressionException {
	    Calculator obj = new Calculator();
	    assertEquals(obj.evaluate("let(a,5,add(let(a,10,mult(a,10)),a))"),"105");
	}

   public void testExample6() throws InvalidExpressionException {
	    Calculator obj = new Calculator();
	    assertEquals(obj.evaluate("let(a,5,add(let(a,let(b,add(3,2),add(b,5)),mult(a,10)),a))"),"105");
	}

   public void testExample7() throws InvalidExpressionException {
	    Calculator obj = new Calculator();
	    assertEquals(obj.evaluate("let  (  abb  ,  5  ,  add   (   let  (a ,  let( b  ,   add    (  3 ,  2  ) ,   add   (  b   , 5  )    ) ,  mult   ( a  , 10 )  ),abb  )   )"),"105");
	}
   
   public void testExample8() throws InvalidExpressionException {
	    Calculator obj = new Calculator();
	    assertEquals(obj.evaluate("let(a,5,add(add(let(a,10,add(a,5)),let(a,5,mult(a,10))),a))"),"70");
	}
}
