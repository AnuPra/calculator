package demo.calculator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

/**
 * Hello world!
 *
 */
public class Calculator 
{
	Stack<String> operators = new Stack<String>();
	Stack<Integer> values = new Stack<Integer>();
	Stack<String> variables = new Stack<String>();
	HashMap<String,Integer> assignedVariables = new HashMap<String, Integer>();
	
	int evaluate(String expr) {
		StringBuilder token = new StringBuilder();
		
		for (int index=0;index<expr.length();index++) {
			char ch = expr.charAt(index);
			System.out.println(token+"---"+ch);
			if (ch ==')') {
				processCloseBrace(token.toString());
				token = new StringBuilder();
			}else if (ch == '(') {
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
		System.out.println("-------Exiting evaluate---------------");
		return values.pop();
	}
	
	void processCloseBrace(String token) {
		System.out.println("Inside processCloseBrace");
		
		if (token.isEmpty()) processCloseBraceForEmptyToken();
		else ProcessCloseBraceForNonEmptyToken(token);

		print();
	}
	
	void processCloseBraceForEmptyToken() {
		String op = operators.pop();
		if (op.equals("let")) {
			String key = variables.pop();
			assignedVariables.remove(key);
		} else applyOperation(op);
		assignVariableIfLet();
	}
	
	void ProcessCloseBraceForNonEmptyToken(String token) {
		
		int value = (checkIfNumber(token) ? Integer.valueOf(token.toString()) : assignedVariables.get(token));
		values.push(value);
			
		String op = operators.pop();
		applyOperation(op);
	}
	
	void applyOperation(String op) {
		int val1 = values.pop();
		int val2 = values.pop();
		int result = Operation.valueOf(op).apply(val1, val2);
		values.push(result);	
	}
	
	void assignVariableIfLet() {
		if (!operators.isEmpty() && operators.peek().equals("let") && !variables.empty()) {
			String key = variables.peek();
			if (!assignedVariables.containsKey(key)) {
				int value = values.pop();
				assignedVariables.put(key, value);
			}
		}
	}
	
	void processOpenBrace(String token) {
		System.out.println("Inside processOpenBrace");
		
		String tokenString = token.toString();
		if (tokenString.equals("let") || Operation.contains(tokenString)) {
			operators.push(tokenString);
		}

		print();
	}
	
	void processComma(String token) {
		System.out.println("Inside processComma");
		
		if (checkIfNumber(token)) processCommaForNumber(token);
		else {
			if (assignedVariables.containsKey(token)) {
				values.push(assignedVariables.get(token));
			} else if (!token.isEmpty()){
				variables.add(token);
			} else {
				assignVariableIfLet();
			}
		}

		print();
	}
	
	void processCommaForNumber(String token) {
		int value = Integer.valueOf(token.toString());
		if (operators.peek().equals("let")) {
			String key = variables.peek();
			assignedVariables.put(key, value);
		} else {
			values.push(value);
		}
	}
	
	void print() {
		System.out.println("Operators:"+Arrays.toString(operators.toArray()));
		System.out.println("values:"+Arrays.toString(values.toArray()));
		System.out.println("assignedVariables:"+Arrays.asList(assignedVariables));
		System.out.println("Variables:"+Arrays.toString(variables.toArray()));
	}
	
	boolean checkIfNumber(String token) {
		System.out.print("Inside checkIfNumber");
		boolean result = true;
		if (token.isEmpty()) result = false;
		for (int i=0;i<token.length();i++) 
			if (!Character.isDigit(token.charAt(i))) {result = false; break;};
		return result;
	}

	public static void main( String[] args )
    {
		Calculator obj = new Calculator();
		obj.evaluate(args[1]);
		System.out.println( "Hello World!" );
    }
}