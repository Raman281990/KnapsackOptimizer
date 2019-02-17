package com.maersk.knapsack.dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maersk.knapsack.annotations.ValidTaskRequest;
import com.maersk.knapsack.jpa.entities.Knapsack;
import com.maersk.knapsack.jpa.entities.Task;

@ValidTaskRequest
public class Problem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("capacity")
    private int capacity;
    
    @JsonProperty("weights")
    private List<Integer> weights;
    
    @JsonProperty("values")
    private List<Integer> values;
    

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

    public List<Integer> getWeights() {
		return weights;
	}

	public void setWeights(List<Integer> weights) {
		this.weights = weights;
	}

	public List<Integer> getValues() {
		return values;
	}

	public void setValues(List<Integer> values) {
		this.values = values;
	}

	public static boolean hasNoValues(Problem value) {
		if(StringUtils.isEmpty(value.getValues()))
			return true;
		else
			return false;
	}
    
    public static boolean hasNoWeights(Problem value) {
		if(StringUtils.isEmpty(value.getWeights()))
			return true;
		else
			return false;
	}
    
    public static boolean hasNegativeValues(Problem value) {
    	if(value.getValues().stream().anyMatch(i ->i<0))
    		return true;
    	else
    		return false;
    }
    public static boolean hasNegativeWeights(Problem value) {
    	if(value.getWeights().stream().anyMatch(i ->i<0))
    		return true;
    	else
    		return false;
    }
    public static boolean hasMismatchedSizes(Problem value) {
    	if(value.getValues().size()!=value.getWeights().size()) {
    		return true;
    	}
    	else
    		return false;
    }
    
    /**createKnapsackProblem create a Knapsack entity corresponding to the
     * submitted problem for persisting in the database.
     * 
     * @param Problem
     * @return Knapsack
     */
    public static Knapsack createKnapsackProblem(Problem request) {
		Knapsack knapsack = new Knapsack();
		knapsack.setCapacity(request.getCapacity());
		String values =request.getValues().stream().map(String::valueOf).collect(Collectors.joining(","));
		String weights =request.getWeights().stream().map(String::valueOf).collect(Collectors.joining(","));
		knapsack.setValues(values);
		knapsack.setWeights(weights);
		return knapsack;
	}
    
    /**getKnapsackProblem method fetches the Knapsack Problem parameters from the task
     * such as Capacity,Values, Weights
     * 
     * @param task
     * @return Problem
     */
    public static Problem getKnapsackProblem(Task task) {
    	Problem problem = new Problem();
    	problem.setCapacity(task.getKnapsack().getCapacity());
    	problem.setValues(IntStream.of(TaskInfo.getValues(task)).boxed().collect(Collectors.toList()));
    	problem.setWeights(IntStream.of(TaskInfo.getWeights(task)).boxed().collect(Collectors.toList()));
    	return problem;
    	
    }

	@Override
	public String toString() {
		return "Problem [capacity=" + capacity + ", weights=" + weights + ", values=" + values + "]";
	}

}
