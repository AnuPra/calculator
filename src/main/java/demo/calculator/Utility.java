package demo.calculator;

public class Utility {

	public static void validateOperator(String op) throws InvalidExpressionException {
		if (!op.equals("let") && !Operation.contains(op)) 
			throw new InvalidExpressionException (op+" is invalid operation");
	}
	
	public static boolean checkIfNumber(String token) {
		if (token.isEmpty()) return false;
		if (token.matches("[0-9]+(.[0-9]+)*")) return true;
		return false;
	}
	
	public static void validateValuesForGivenOperation(double x, String op) throws InvalidExpressionException {
		if (Operation.div.toString().equals(op.trim()) && x==0) {
			throw new InvalidExpressionException("Invalid denominator for division operation");
		}
	}
}