package demo.calculator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class CalculatorTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CalculatorTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( CalculatorTest.class);
    }
    
    public void testAppWithNestedLet()
    {
        Calculator obj = new Calculator();
        assertEquals(obj.evaluate("let(a,let(b,10,add(b,b)),let(b,20,add(a,b))"),40);
    }
    
    public void testAppWithNestedLet1()
    {
        Calculator obj = new Calculator();
        assertEquals(obj.evaluate("let(a,5,let(b,mult(a,10),add(b,a)))"),55);
    }
    
    public void testAppWithLet()
    {
        Calculator obj = new Calculator();
        assertEquals(obj.evaluate("let(a,5,add(a,a))"),10);
        //assertEquals(obj.evaluate("let(b,let(b,5,add(b,5)),add(b,5))"),15);
        //assertEquals(obj.evaluate("let(a,let(b,add(mult(10,2),mult(5,3)),add(b,10)),add(a,20))"),65);
        //assertEquals(obj.evaluate("let(a,let(b,let(a,let(b,add(10,5),add(b,3)),add(a,5)),add(b,3)),add(a,6))"),32);
        //assertEquals(obj.evaluate("let(a,5,let(b,let(a,10,add(a,5)),mult(b,a)))"),75);
    }
    
    public void testAppWithNestedOp()
    {
        Calculator obj = new Calculator();
        assertEquals(obj.evaluate("mult(add(2,2),div(9,3))"),12);
    }
    
    public void testAppWithLet1()
    {
        Calculator obj = new Calculator();
        assertEquals(obj.evaluate("add(1,mult(2,3))"),7);
    }
    
    public void testAppWithAdd()
    {
        Calculator obj = new Calculator();
        assertEquals(obj.evaluate("add(1,2)"),3);
    }

}
