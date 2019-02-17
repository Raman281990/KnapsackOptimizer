package com.maersk.knapsack.service;

import com.maersk.knapsack.jpa.entities.Task;

public interface TaskProcessingService {
	public Task processTasks(Task task);
}