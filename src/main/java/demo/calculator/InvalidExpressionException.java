package demo.calculator;

//Custom exception class for exceptions
public class InvalidExpressionException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidExpressionException(String message) {
        super(message);
    }
}
