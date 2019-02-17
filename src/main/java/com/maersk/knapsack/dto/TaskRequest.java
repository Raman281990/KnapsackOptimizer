package com.maersk.knapsack.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;


public class TaskRequest {
	
	@JsonProperty("problem")
	@NotNull
	@Valid
	private Problem problem;

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	@Override
	public String toString() {
		return "TaskRequest [problem=" + problem + "]";
	}
	

}
