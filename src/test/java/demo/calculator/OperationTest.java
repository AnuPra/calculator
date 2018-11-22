package demo.calculator;

import java.util.Random;

import junit.framework.TestCase;

public class OperationTest extends TestCase {

	private int generateRandomNum() {
		Random r = new Random();
		return r.nextInt(1000);
		
	}
	
	public void testAddition() {
		int val1 = generateRandomNum(), val2 = generateRandomNum();
		int result = Operation.valueOf("add").apply(val1, val2);
		assertEquals(result, (val1+val2));
	}
	
	public void testSubtraction() {
		int val1 = generateRandomNum(), val2 = generateRandomNum();
		int result = Operation.valueOf("sub").apply(val1, val2);
		assertEquals(result, (val1-val2));
	}

	public void testDivision() {
		int val1 = generateRandomNum(), val2 = generateRandomNum();
		int result = Operation.valueOf("div").apply(val1, val2);
		assertEquals(result, (val2/val1));
	}

	public void testMultiplication() {
		int val1 = generateRandomNum(), val2 = generateRandomNum();
		System.out.println(val1+":"+val2);
		int result = Operation.valueOf("mult").apply(val1, val2);
		assertEquals(result, (val1*val2));
	}

}