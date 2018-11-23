package demo.calculator;

public class Utility {

	//Validates if input is valid operation
	public static void validateOperator(String op) throws InvalidExpressionException {
		if (!op.equals("let") && !Operation.contains(op)) 
			throw new InvalidExpressionException (op+" is invalid operation");
	}
	
	//Check if input is valid number with decimal
	public static boolean checkIfNumber(String token) {
		if (token.isEmpty()) return false;
		if (token.matches("[0-9]+(.[0-9]+)*")) return true;
		return false;
	}
	
	//Validates input for given operation
	public static void validateValuesForGivenOperation(double x, String op) throws InvalidExpressionException {
		if (Operation.div.toString().equals(op.trim()) && x==0) {
			throw new InvalidExpressionException("Invalid denominator for division operation");
		}
	}
}