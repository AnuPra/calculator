package demo.calculator;

public class Utility {

	public static void validateOperator(String op) throws InvalidExpressionException {
		if (!op.equals("let") && !Operation.contains(op)) 
			throw new InvalidExpressionException (op+" is invalid operation");
	}
	
	public static boolean checkIfNumber(String token) {
		boolean result = true;
		if (token.isEmpty()) result = false;
		for (int i=0;i<token.length();i++) 
			if (!Character.isDigit(token.charAt(i))) {result = false; break;};
		return result;
	}
	
	public static void validateValuesForGivenOperation(double x, String op) throws InvalidExpressionException {
		if (Operation.div.toString().equals(op.trim()) && x==0) {
			throw new InvalidExpressionException("Invalid denominator for division operation");
		}
	}
}