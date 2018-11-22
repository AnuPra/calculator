package demo.calculator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;
import org.apache.logging.log4j.*;

import demo.calculator.Operation;
import demo.calculator.Utility;

public class Calculator 
{
	private final static org.apache.logging.log4j.Logger LOG = LogManager.getRootLogger();
			
	Stack<String> operators = new Stack<String>();
	Stack<Double> values = new Stack<Double>();
	Stack<String> variables = new Stack<String>();
	HashMap<String,Double> assignedVariables = new HashMap<String, Double>();
	
	String evaluate(String expr) throws InvalidExpressionException {
		LOG.log(Level.DEBUG, "Entering evaluate()");
		LoggerConfig.setLogLevel("0");
		
		StringBuilder token = new StringBuilder();
		int open=0;
		
		for (int index=0;index<expr.length();index++) {
			char ch = expr.charAt(index);
			LOG.log(Level.DEBUG, (ch+"--->"+token));
			if (ch ==')') {
				open--;
				processCloseBrace(token.toString());
				token = new StringBuilder();
			}else if (ch == '(') {
				open++;
				processOpenBrace(token.toString());
				token = new StringBuilder();
			} else if (ch ==',') {
				processComma(token.toString());
				token = new StringBuilder();
			} else {
				token.append(ch);
				while(index+1<expr.length() && (ch=expr.charAt(index+1))!=',' && ch!='(' && ch!=')') {
					index++;
					token.append(ch);
				}
			}	
		}
		LOG.log(Level.DEBUG, "-------Exiting evaluate---------------");
		if (open!=0) throw new InvalidExpressionException("unbalanced parenthesis");
		double result = values.pop();
		
		if (String.valueOf(result).endsWith(".0")) 
			return (String.valueOf(result).substring(0, String.valueOf(result).length()-2));
		else
			return String.valueOf(result);
	}
	
	void processCloseBrace(String token) throws InvalidExpressionException {
		LOG.log(Level.DEBUG, "Inside processCloseBrace");
		
		if (token.isEmpty()) processCloseBraceForEmptyToken();
		else ProcessCloseBraceForNonEmptyToken(token);

		print();
	}
	
	void processCloseBraceForEmptyToken() throws InvalidExpressionException {
		String op = operators.pop();
		if (op.equals("let")) {
			String key = variables.pop();
			assignedVariables.remove(key);
		} else applyOperation(op);
		assignVariableIfLetOnTopOfOperatorStack();
	}
	
	void ProcessCloseBraceForNonEmptyToken(String token) throws InvalidExpressionException {
		
		double value = (Utility.checkIfNumber(token) ? Double.valueOf(token.toString()) : assignedVariables.get(token));
		values.push(value);
			
		String op = operators.pop();
		applyOperation(op);
	}
	
	void applyOperation(String op) throws InvalidExpressionException {
		if (values.empty()) throw new InvalidExpressionException("Invalid arguments");
		double val1 = values.pop();

		if (values.empty()) throw new InvalidExpressionException("Invalid arguments");
		double val2 = values.pop();
		
		System.out.println(val1+"<"+val2);
		Utility.validateValuesForGivenOperation(val1, op);
		double result = Operation.valueOf(op).apply(val1, val2);
		values.push(result);	
	}
	
	void assignVariableIfLetOnTopOfOperatorStack() {
		if (!operators.isEmpty() && operators.peek().equals("let") && !variables.empty()) {
			String key = variables.peek();
			if (!assignedVariables.containsKey(key)) {
				double value = values.pop();
				assignedVariables.put(key, value);
			}
		}
	}
	
	void processOpenBrace(String token) throws InvalidExpressionException {
		LOG.log(Level.DEBUG, "Inside processOpenBrace");
		
		if (token.equals("let") || Operation.contains(token)) operators.push(token);
		else throw new InvalidExpressionException("Invalid operation");
		
		print();
	}
	
	void processComma(String token) {
		LOG.log(Level.DEBUG, "Inside processComma");
		
		if (Utility.checkIfNumber(token)) processCommaForNumber(token);
		else {
			if (assignedVariables.containsKey(token)) {
				values.push(assignedVariables.get(token));
			} else if (!token.isEmpty()){
				variables.add(token);
			} else {
				assignVariableIfLetOnTopOfOperatorStack();
			}
		}

		print();
	}
	
	void processCommaForNumber(String token) {
		double value = Integer.valueOf(token.toString());
		if (operators.peek().equals("let")) {
			String key = variables.peek();
			assignedVariables.put(key, value);
		} else { values.push(value); }
	}
	
	void print() {
		LOG.log(Level.DEBUG, "Operators:"+Arrays.toString(operators.toArray()));
		LOG.log(Level.DEBUG, "values:"+Arrays.toString(values.toArray()));
		LOG.log(Level.DEBUG, "assignedVariables:"+Arrays.asList(assignedVariables));
		LOG.log(Level.DEBUG, "Variables:"+Arrays.toString(variables.toArray()));
	}
	
	public static void main( String[] args )
    {
		LOG.log(Level.DEBUG, "Entered main()");
		
		if (args.length <=0  || args[0].isEmpty()) {
			LOG.log(Level.ERROR, "Missing expression");
			return;
		}
		
		String expr = args[0];
		if (expr.trim().isEmpty()) {
			LOG.log(Level.ERROR, "Blank expression");
			return;
		}
		
		try {
			Calculator obj = new Calculator();
			
			String result = obj.evaluate(expr);
			System.out.println(result);
		} catch (Exception ex) {
			LOG.log(Level.ERROR, ex.getMessage());
		}
    }
}