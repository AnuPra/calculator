package demo.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

/**
 * Operations for following data structures:
 * 1) Stack to track order of variables encountered from beginning of expression. Allows duplicates.
 * 2) HashMap of assigned variables. Value is a list, so that duplicate variables within different scopes can be stored.
 * While parsing expression from left to right, for given variable name, values are added and removed from end.
 * @author anusha
 */
public class ExpressionVariables {

	private final static org.apache.logging.log4j.Logger LOG = LogManager.getRootLogger();

	Stack<String> variables = new Stack<String>();
	HashMap<String,List<Double>> assignedVariables = new HashMap<String, List<Double>>();
	
	/**
	 * Removes out of scope variables
	 */
	public void removeOutOfScopeVariable() {
		String key = variables.pop();
		List<Double> values = assignedVariables.get(key);
		int size = values.size();
		if (size == 1)
			assignedVariables.remove(key);
		else {
			values.remove(size-1);
			assignedVariables.put(key, values);
		}	
	}
	
	/**
	 * Retrieves value of variable, if assigned. Else returns empty.
	 * @param key
	 * @return
	 */
	public Optional<Double> getAssignedValue(String key) {
		if (assignedVariables.containsKey(key)) {
			List<Double> values = assignedVariables.get(key);
			int size = values.size();
			return Optional.of(values.get(size-1));
		}
		else return Optional.empty();
	}

	/**
	 * Assigns value to top member of variable stack
	 * @param value
	 * @return
	 */
	public boolean assignVariable(Double value) {
		if (!variables.empty()) {
			if (!assignedVariables.containsKey(variables.peek())) {
				List<Double> values = new ArrayList<Double>();
				values.add(value);
				assignedVariables.put(variables.peek(), values);
			} else {
				List<Double> values = assignedVariables.get(variables.peek());
				values.add(value);
				assignedVariables.put(variables.peek(), values);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Adds token to top of variable stack.
	 * @param token
	 */
	public void addVariable(String token) {
		variables.add(token);
	}
	
	/**
	 * Prints assigned variables and value stack
	 */
	public void print() {
		LOG.log(Level.DEBUG, "assignedVariables:"+Arrays.asList(assignedVariables));
		LOG.log(Level.DEBUG, "Variables:"+Arrays.toString(variables.toArray()));
	}
}
