package com.maersk.knapsack.service;

import java.util.List;
import java.util.Optional;

import com.maersk.knapsack.jpa.entities.Task;

public interface TaskService {
	
	public Optional<List<Task>> getAllSubmittedTasks();
	
	public int markAsStarted(List<Task> tasks);
	
	public void saveTasks(Task task);
	
	public Task getTaskById(String taskId);
	
	public Optional<List<Task>> getAllTasks();
	
}
