package demo.calculator;

import java.util.Arrays;
import java.util.Optional;
import java.util.Stack;
import org.apache.logging.log4j.*;

import demo.calculator.Operation;
import demo.calculator.Utility;

public class Calculator 
{
	private final static org.apache.logging.log4j.Logger LOG = LogManager.getRootLogger();
			
	Stack<String> operators = new Stack<String>();
	Stack<Double> values = new Stack<Double>();
	ExpressionVariables var = new ExpressionVariables();
	
	String evaluate(String expr) throws InvalidExpressionException {
		LOG.log(Level.DEBUG, "Entering evaluate()");
		
		StringBuilder token = new StringBuilder();
		int open=0;
		
		for (int index=0;index<expr.length();index++) {
			char ch = expr.charAt(index);
	
			token = new StringBuilder();
			while(index<expr.length() && (ch=expr.charAt(index))!=',' && ch!='(' && ch!=')') {
				token.append(ch);
				index++;
			}
			LOG.log(Level.DEBUG, (token+""+ch));
			
			if (ch ==')') {
				open--;
				processTokenBeforeCloseBrace(token.toString());
			}else if (ch == '(') {
				open++;
				processTokenBeforeOpenBrace(token.toString());
			} else if (ch ==',') {
				processTokenBeforeComma(token.toString());
			} 
			print();
		}
		
		if (open!=0) throw new InvalidExpressionException("unbalanced parenthesis");
		
		LOG.log(Level.DEBUG, "-------Exiting evaluate---------------");
		
		return formatOutput();
	}
	
	String formatOutput() {
		double result = values.pop();
		
		if (String.valueOf(result).endsWith(".0")) 
			return (String.valueOf(result).substring(0, String.valueOf(result).length()-2));
		else
			return String.valueOf(result);
	}
	
	void processTokenBeforeCloseBrace(String token) throws InvalidExpressionException {
		LOG.log(Level.DEBUG, "Inside processCloseBrace");
		
		if (token.isEmpty()) processEmptyTokenBeforeCloseBrace();
		else processNonEmptyTokenBeforeCloseBrace(token);
	}
	
	void processEmptyTokenBeforeCloseBrace() throws InvalidExpressionException {
		String op = operators.pop();
		if (op.equals("let")) var.removeOutOfScopeVariable();
		else applyOperation(op);

		if (!operators.isEmpty() && operators.peek().equals("let")) {
			boolean result = var.assignVariableIfNotAlreadyAssigned(values.empty()?null:values.peek());
			if (result) values.pop();
		}
	}
	
	void processNonEmptyTokenBeforeCloseBrace(String token) throws InvalidExpressionException {
		double value = (Utility.checkIfNumber(token) ? Double.valueOf(token.toString()) : var.getAssignedValue(token).get());
		values.push(value);	
		String op = operators.pop();
		applyOperation(op);
	}
	
	void applyOperation(String op) throws InvalidExpressionException {
		if (values.size() < 2) throw new InvalidExpressionException("Invalid arguments");
		double val1 = values.pop();
		double val2 = values.pop();
		
		Utility.validateValuesForGivenOperation(val1, op);
		double result = Operation.valueOf(op).apply(val1, val2);
		values.push(result);	
	}
	
	void processTokenBeforeOpenBrace(String token) throws InvalidExpressionException {
		LOG.log(Level.DEBUG, "Inside processOpenBrace");
		
		Utility.validateOperator(token);
		operators.push(token);
	}
	
	void processTokenBeforeComma(String token) throws InvalidExpressionException {
		LOG.log(Level.DEBUG, "Inside processComma");
		
		if (operators.isEmpty()) throw new InvalidExpressionException("Invalid operator");
		
		if (Utility.checkIfNumber(token)) processNumberBeforeComma(token);
		else if (token.isEmpty()) processEmptyTokenBeforeComma();
		else processVariableBeforeComma(token);
	}
	
	void processNumberBeforeComma(String token) {
		double value = Double.valueOf(token.toString());
		if (operators.peek().equals("let")) var.assignVariable(value);
		else values.push(value); 
	}
	
	void processEmptyTokenBeforeComma() {
		if (operators.peek().equals("let")) {
			boolean result = var.assignVariableIfNotAlreadyAssigned(values.empty()?null:values.peek());
			if (result) values.pop();
		}
	}
	
	void processVariableBeforeComma(String token) {
		if (operators.peek().equals("let")) var.addVariable(token);
		else {
			Optional<Double> val = var.getAssignedValue(token);
			if (val.isPresent()) values.push(val.get());
		}
	}
	
	void print() {
		LOG.log(Level.DEBUG, "Operators:"+Arrays.toString(operators.toArray()));
		LOG.log(Level.DEBUG, "values:"+Arrays.toString(values.toArray()));
		var.print();
	}
	
	public static void main( String[] args )
    {
		LOG.log(Level.DEBUG, "Entered main()");
		
		if (args.length <=0  || args[0].trim().isEmpty()) {
			LOG.log(Level.ERROR, "Missing expression");
			return;
		}
		
		try {
			Calculator obj = new Calculator();
			
			String result = obj.evaluate(args[0]);
			System.out.println(result);
		} catch (Exception ex) {
			LOG.log(Level.ERROR, ex.getMessage());
		}
    }
}