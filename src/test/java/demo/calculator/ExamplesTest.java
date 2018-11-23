package demo.calculator;

import demo.calculator.Calculator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ExamplesTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ExamplesTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ExamplesTest.class);
    }
    
    public void testAppWithNestedLet() throws InvalidExpressionException
    {
        Calculator obj = new Calculator();
        assertEquals(obj.evaluate("let(a,let(b,10,add(b,b)),let(b,20,add(a,b)))"),"40");
    }
    
    public void testAppWithNestedLet1() throws InvalidExpressionException
    {
        Calculator obj = new Calculator();
        assertEquals(obj.evaluate("let(a,5,let(b,mult(a,10),add(b,a)))"),"55");
    }
    
    public void testAppWithLet() throws InvalidExpressionException
    {
        Calculator obj = new Calculator();
        assertEquals(obj.evaluate("let(a,5,add(a,a))"),"10");
     }
    
    public void testAppWithNestedOp() throws InvalidExpressionException
    {
        Calculator obj = new Calculator();
        assertEquals(obj.evaluate("mult(add(2,2),div(9,3))"),"12");
    }
    
    public void testAppWithLet1() throws InvalidExpressionException
    {
        Calculator obj = new Calculator();
        assertEquals(obj.evaluate("add(1,mult(2,3))"),"7");
    }
    
    public void testAppWithAdd() throws InvalidExpressionException
    {
        Calculator obj = new Calculator();
        assertEquals(obj.evaluate("add(1,2)"),"3");
    }

}
