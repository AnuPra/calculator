package demo.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class ExpressionVariables {

	private final static org.apache.logging.log4j.Logger LOG = LogManager.getRootLogger();

	Stack<String> variables = new Stack<String>();
	HashMap<String,List<Double>> assignedVariables = new HashMap<String, List<Double>>();
	
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
	
	public Optional<Double> getAssignedValue(String key) {
		if (assignedVariables.containsKey(key)) {
			List<Double> values = assignedVariables.get(key);
			int size = values.size();
			return Optional.of(values.get(size-1));
		}
		else return Optional.empty();
	}

	
	public boolean assignVariableIfNotAlreadyAssigned(Double value) {
		if (!variables.empty()) {
			if (!assignedVariables.containsKey(variables.peek())) {
				List<Double> values = new ArrayList<Double>();
				values.add(value);
				assignedVariables.put(variables.peek(), values);
				return true;
			}
		}
		return false;
	}
	
	public void assignVariable(Double value) {
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
		}
	}
	
	public void addVariable(String token) {
		variables.add(token);
	}
	
	public void print() {
		LOG.log(Level.DEBUG, "assignedVariables:"+Arrays.asList(assignedVariables));
		LOG.log(Level.DEBUG, "Variables:"+Arrays.toString(variables.toArray()));
	}
}
