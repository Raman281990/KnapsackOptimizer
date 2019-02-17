package com.maersk.knapsack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskStatusTime {
	
	@JsonProperty("submitted")
	private Long submitted;

	@JsonProperty("started")
    private Long started;

	@JsonProperty("completed")
    private Long completed;

	public Long getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Long submitted) {
		this.submitted = submitted;
	}

	public Long getStarted() {
		return started;
	}

	public void setStarted(Long started) {
		this.started = started;
	}

	public Long getCompleted() {
		return completed;
	}

	public void setCompleted(Long completed) {
		this.completed = completed;
	}
	
}
