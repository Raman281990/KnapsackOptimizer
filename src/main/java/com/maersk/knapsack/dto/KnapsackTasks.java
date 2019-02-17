package com.maersk.knapsack.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KnapsackTasks {
	
	@JsonProperty("submitted")
	List<TaskInfo> submitted;
	
	@JsonProperty("started")
	List<TaskInfo> started;
	
	@JsonProperty("completed")
	List<TaskInfo> completed;

	
	public KnapsackTasks() {
		super();
	}

	public KnapsackTasks(List<TaskInfo> submitted, List<TaskInfo> started, List<TaskInfo> completed) {
		super();
		this.submitted = submitted;
		this.started = started;
		this.completed = completed;
	}

	public List<TaskInfo> getSubmitted() {
		return submitted;
	}

	public void addSubmittedTasks(List<TaskInfo> submitted) {
		this.submitted = submitted;
	}

	public List<TaskInfo> getStarted() {
		return started;
	}

	public void addStartedTasks(List<TaskInfo> started) {
		this.started = started;
	}

	public List<TaskInfo> getCompleted() {
		return completed;
	}

	public void addCompletedTasks(List<TaskInfo> completed) {
		this.completed = completed;
	}
	
	

}
