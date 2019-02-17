package com.maersk.knapsack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AllTasks { 
	
	@JsonProperty("tasks")
	KnapsackTasks tasks;

	public KnapsackTasks getTasks() {
		return tasks;
	}

	public void addTasks(KnapsackTasks tasks) {
		this.tasks = tasks;
	}
	
	
}