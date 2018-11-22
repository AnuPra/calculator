package demo.calculator;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;

public enum Operation {
	add  ("add", (x,y) -> x+y),
	sub  ("sub", (x,y) -> x-y),
	mult ("mult", (x,y) -> x*y),
	div  ("div", (x,y) -> y/x);
	
	private final String symbol;
	private final IntBinaryOperator op;
	
	Operation(String symbol, IntBinaryOperator op) {
		this.symbol = symbol;
		this.op = op;
	}
	
	@Override
	public String toString() {
		return symbol;
	}
	
	public int apply(int x, int y) {
		return op.applyAsInt(x, y);
	}
	
	public static boolean contains(String token) {
		return Arrays.stream(Operation.values())
				.anyMatch(op -> op.symbol.equals(token));
	}
}