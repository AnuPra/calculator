package demo.calculator;

import java.util.Arrays;
import java.util.function.DoubleBinaryOperator;

//Supports addition, subtraction, multiplication and division for two double values
public enum Operation {
	add  ("add", (x,y) -> x+y),
	sub  ("sub", (x,y) -> x-y),
	mult ("mult", (x,y) -> x*y),
	div  ("div", (x,y) -> y/x);
	
	private final String symbol;
	private final DoubleBinaryOperator op;
	
	Operation(String symbol, DoubleBinaryOperator op) {
		this.symbol = symbol;
		this.op = op;
	}
	
	@Override
	public String toString() {
		return symbol;
	}
	
	public double apply(double x, double y) {
		return op.applyAsDouble(x, y);
	}
	
	public static boolean contains(String token) {
		return Arrays.stream(Operation.values())
				.anyMatch(op -> op.symbol.equals(token));
	}
}