package com.maersk.knapsack.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.maersk.knapsack.constants.KnapsackOptimizerConstants;
import com.maersk.knapsack.jpa.entities.Task;
import com.maersk.knapsack.jpa.entities.Task.Status;

public class TaskInfo {

	@JsonProperty("task")
	@JsonInclude(Include.NON_NULL)
	private String task;

	@JsonProperty("status")
	@JsonInclude(Include.NON_NULL)
	private String status;

	@JsonProperty("timestamps")
	@JsonInclude(Include.NON_NULL)
	private TaskStatusTime timestamps;
	
	@JsonInclude(Include.NON_NULL)
	private Error error;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public TaskStatusTime getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(TaskStatusTime timestamps) {
		this.timestamps = timestamps;
	}
	public static TaskInfo mapTaskSubmissionResponse(Task tasks) {
		TaskInfo task= new TaskInfo();
		task.setStatus(tasks.getTaskStatus());
		task.setTask(tasks.getTaskId());
		TaskStatusTime taskStatusTime = new TaskStatusTime();
		taskStatusTime.setSubmitted(tasks.getTaskSubmittedTime());
		task.setTimestamps(taskStatusTime);
		return task;
	}
	public static  Task createTask() {
		Task task = new Task();
		task.setTaskStatus(Status.SUBMITTED.getValue());
		task.setTaskSubmittedTime(System.currentTimeMillis() / 1000L);
		return task;
	}
	public static int[] getValues(Task task) {
		int[] values= Stream.of(task.getKnapsack().getValues()
				.split(",")).mapToInt(Integer::valueOf).toArray();
		return values;
	}
	
	public static int[] getWeights(Task task) {
		int[] weights= Stream.of(task.getKnapsack().getWeights()
				.split(",")).mapToInt(Integer::valueOf).toArray();
		return weights;
	}
	public static TaskInfo taskResponseMapper(Task tasks) {
		TaskInfo task = new TaskInfo();
		task.setTask(tasks.getTaskId().toString());
		task.setStatus(tasks.getTaskStatus());
		TaskStatusTime taskStatusTime = new TaskStatusTime();
		taskStatusTime.setSubmitted(tasks.getTaskSubmittedTime());
		taskStatusTime.setStarted(tasks.getTaskStartedTime());
		taskStatusTime.setCompleted(tasks.getTaskCompletedTime());
		task.setTimestamps(taskStatusTime);
		return task;
	}
	public static Long extractTaskId(String taskName) {
		String taskId =taskName.substring(KnapsackOptimizerConstants.TASK_ID_PREFIX.length()-1);
		return Long.valueOf(taskId);
	}
	public static List<TaskInfo> mapTasks(List<Task> tasks) {
		return tasks.stream().map(task ->TaskInfo.taskResponseMapper(task)).collect(Collectors.toList());
	}

	public static List<Task> filterCompletedTasks(List<Task> tasks) {
		return tasks.stream()
				.filter(task -> task.getTaskStatus().equalsIgnoreCase(Status.COMPLETED.getValue()))
				.collect(Collectors.toList());
	}

	public static List<Task> filterStartedTasks(List<Task> tasks) {
		return tasks.stream()
				.filter(task -> task.getTaskStatus().equalsIgnoreCase(Status.STARTED.getValue()))
				.collect(Collectors.toList());
	}

	public static List<Task> filterSubmittedTasks(List<Task> tasks) {
		return tasks.stream()
				.filter(task -> task.getTaskStatus().equalsIgnoreCase(Status.SUBMITTED.getValue()))
				.collect(Collectors.toList());
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

}
