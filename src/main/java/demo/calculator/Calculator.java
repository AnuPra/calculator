package demo.calculator;

import java.util.Arrays;
import java.util.Optional;
import java.util.Stack;
import org.apache.logging.log4j.*;

import demo.calculator.Operation;
import demo.calculator.Utility;

/**
 * Main class to parse expression from left to right and to print computed value.
 * @author anusha
 */
public class Calculator 
{
	private final static org.apache.logging.log4j.Logger LOG = LogManager.getRootLogger();
			
	Stack<String> operators = new Stack<String>();
	Stack<Double> values = new Stack<Double>();
	ExpressionVariables var = new ExpressionVariables();
	
	/**
	 * Parses the expression from left to right into tokens and symbols - '(' , ')' and ','. 
	 * As per symbol, token processing is carried out in respective method.
	 * @param expr
	 * @return result
	 * @throws InvalidExpressionException
	 */
	String evaluate(String expr) throws InvalidExpressionException {
		LOG.log(Level.TRACE, "Entering evaluate()");
		
		StringBuilder token = new StringBuilder();
		int open=0;
		
		for (int index=0;index<expr.length();index++) {
			char ch = expr.charAt(index);
	
			token = new StringBuilder();
			while(index<expr.length() && (ch=expr.charAt(index))!=',' && ch!='(' && ch!=')') {
				token.append(ch);
				index++;
			}
			LOG.log(Level.DEBUG, ("------------- Processing  "+token+""+ch));
			
			if (ch ==')') {
				open--;
				processTokenBeforeCloseBrace(token.toString().trim());
			}else if (ch == '(') {
				open++;
				processTokenBeforeOpenBrace(token.toString().trim());
			} else if (ch ==',') {
				processTokenBeforeComma(token.toString().trim());
			} 
			print();
		}
		
		if (open!=0) throw new InvalidExpressionException("unbalanced parenthesis");
		
		LOG.log(Level.DEBUG, "-------Exiting evaluate---------------");
		
		return formatOutput();
	}
	
	/**
	 * Removes decimal part if it is zero, else returns with decimal part.
	 * @return
	 */
	String formatOutput() {
		LOG.log(Level.TRACE, "Entering formatOutput()");
		double result = values.pop();
		
		if (String.valueOf(result).endsWith(".0")) 
			return (String.valueOf(result).substring(0, String.valueOf(result).length()-2));
		else
			return String.valueOf(result);
	}
	
	/**
	 * Invokes methods based on empty or non-empty token before closing brace.
	 * @param token
	 * @throws InvalidExpressionException
	 */
	void processTokenBeforeCloseBrace(String token) throws InvalidExpressionException {
		LOG.log(Level.TRACE, "Inside processTokenBeforeCloseBrace()");
		
		if (token.isEmpty()) processEmptyTokenBeforeCloseBrace();
		else processNonEmptyTokenBeforeCloseBrace(token);
	}
	
	/**
	 * If top operator is 'let', removes variables out of scope; Else applies operator on top 2 value stack members. 
	 * @throws InvalidExpressionException
	 */
	void processEmptyTokenBeforeCloseBrace() throws InvalidExpressionException {
		LOG.log(Level.TRACE, "Entering processEmptyTokenBeforeCloseBrace()");
		String op = operators.pop();
		if (op.equals("let")) var.removeOutOfScopeVariable();
		else applyOperation(op);
	}
	
	/**
	 * If token before close brace is:
	 * 1) Number - Pushes to stack
	 * 2) Variable - Gets value from assigned variables, if present. Else throws exception.
	 * Applies operation on top 2 members of value stack.
	 * @param token
	 * @throws InvalidExpressionException
	 */
	void processNonEmptyTokenBeforeCloseBrace(String token) throws InvalidExpressionException {
		LOG.log(Level.TRACE, "Entering processNonEmptyTokenBeforeCloseBrace()");
		double value;
		if (Utility.checkIfNumber(token)) {
			value = Double.valueOf(token.toString());
		} else {
			Optional<Double> result = var.getAssignedValue(token); 
			if (result.isPresent())
				value = result.get();
			else
				throw new InvalidExpressionException("Invalid arguments");
		}
		values.push(value);	
		String op = operators.pop();
		applyOperation(op);
	}
	
	/**
	 * Pops 2 values and applies given operation on them.
	 * @param op
	 * @throws InvalidExpressionException
	 */
	void applyOperation(String op) throws InvalidExpressionException {
		LOG.log(Level.TRACE, "Entering applyOperation()");
		if (values.size() < 2) throw new InvalidExpressionException("Invalid arguments");
		double val1 = values.pop();
		double val2 = values.pop();
		
		Utility.validateValuesForGivenOperation(val1, op);
		double result = Operation.valueOf(op).apply(val1, val2);
		values.push(result);	
	}
	
	/**
	 * Validates token before open brace and pushes to operator stack.
	 * @param token
	 * @throws InvalidExpressionException
	 */
	void processTokenBeforeOpenBrace(String token) throws InvalidExpressionException {
		LOG.log(Level.DEBUG, "Inside processTokenBeforeOpenBrace()");
		
		Utility.validateOperator(token);
		operators.push(token);
	}
	
	/**
	 * Invokes respective methods based on if token is number or empty or variable.
	 * @param token
	 * @throws InvalidExpressionException
	 */
	void processTokenBeforeComma(String token) throws InvalidExpressionException {
		LOG.log(Level.TRACE, "Inside processTokenBeforeComma()");
		
		if (operators.isEmpty()) throw new InvalidExpressionException("Invalid operator");
		
		if (Utility.checkIfNumber(token)) processNumberBeforeComma(token);
		else if (token.isEmpty()) processEmptyTokenBeforeComma();
		else processVariableBeforeComma(token);
	}
	
	/**
	 * If top member of operator stack is 'let' assigns token to top member of variable stack
	 * Else pushes token to value stack.
	 * @param token
	 */
	void processNumberBeforeComma(String token) {
		LOG.log(Level.TRACE, "Entering processNumberBeforeComma()");
		
		double value = Double.valueOf(token.toString());
		if (operators.peek().equals("let")) var.assignVariable(value);
		else values.push(value); 
	}
	
	/**
	 * If top member of operator stack is 'let' assigns top member of value stack to top member of variable stack 
	 */
	void processEmptyTokenBeforeComma() {
		LOG.log(Level.TRACE, "Entering processEmptyTokenBeforeComma()");
		
		if (operators.peek().equals("let")) {
			boolean result = var.assignVariable(values.empty()?null:values.peek());
			if (result) values.pop();
		} 
	}
	
	/**
	 * If top member of operator stack is 'let' assigns token to variable stack
	 * Else if variable is assigned, then its value is pushed to value stack.
	 * @param token
	 */
	void processVariableBeforeComma(String token) {
		LOG.log(Level.TRACE, "Entering processVariableBeforeComma()");
		
		if (operators.peek().equals("let")) var.addVariable(token);
		else {
			Optional<Double> val = var.getAssignedValue(token);
			if (val.isPresent()) values.push(val.get());
		}
	}
	
	/**
	 * Prints Operator stack, value stack, assigned variables and stack of variables.
	 */
	void print() {
		LOG.log(Level.TRACE, "Entering print()");
		
		LOG.log(Level.DEBUG, "Operators:"+Arrays.toString(operators.toArray()));
		LOG.log(Level.DEBUG, "values:"+Arrays.toString(values.toArray()));
		var.print();
	}
	
	/***
	 * Check that first argument is expression and invokes the computation.
	 * @param args
	 */
	public static void main( String[] args )
    {
		LOG.log(Level.DEBUG, "Entered main()");
		
		if (args.length <=0  || args[0].trim().isEmpty()) {
			LOG.log(Level.ERROR, "Missing expression");
			return;
		}
	
		LoggerConfig config = new LoggerConfig();
		if (args.length>1) {
			config.setLogLevel(args[1]);
		} else {
			config.setLogLevel("2");
		}
		
		try {
			Calculator obj = new Calculator();
			
			String result = obj.evaluate(args[0]);
			LOG.log(Level.INFO, result);
			System.out.println(result);
		} catch (Exception ex) {
			LOG.log(Level.ERROR, ex.getMessage());
		}
    }
}