package demo.calculator;

import java.util.Random;

import demo.calculator.Operation;
import junit.framework.TestCase;

public class OperationTest extends TestCase {

	private double generateRandomNum() {
		Random r = new Random();
		int val = r.nextInt(Integer.MAX_VALUE);
		return (double)val;
	}
	
	public void testAddition() {
		double val1 = generateRandomNum(), val2 = generateRandomNum();
		double result = Operation.valueOf("add").apply(val1, val2);
		assertEquals(result, (val1+val2));
	}
	
	public void testSubtraction() {
		double val1 = generateRandomNum(), val2 = generateRandomNum();
		double result = Operation.valueOf("sub").apply(val1, val2);
		assertEquals(result, (val1-val2));
	}

	public void testDivision() {
		double val1 = generateRandomNum(), val2 = generateRandomNum();
		double result = Operation.valueOf("div").apply(val1, val2);
		assertEquals(result, (val2/val1));
	}

	public void testMultiplication() {
		double val1 = generateRandomNum(), val2 = generateRandomNum();
		System.out.println(val1+":"+val2);
		double result = Operation.valueOf("mult").apply(val1, val2);
		assertEquals(result, (val1*val2));
	}

}