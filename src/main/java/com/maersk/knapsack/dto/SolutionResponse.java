package com.maersk.knapsack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolutionResponse {

	@JsonProperty("task")
	private String task;

	@JsonProperty("problem")
	private Problem problem;

	@JsonProperty("solution")
	private Solution solution;

	
	public SolutionResponse() {
		super();
	}

	public SolutionResponse(String task, Problem problem, Solution solution) {
		super();
		this.task = task;
		this.problem = problem;
		this.solution = solution;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	
}
