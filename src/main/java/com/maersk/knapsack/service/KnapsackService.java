package com.maersk.knapsack.service;

import com.maersk.knapsack.dto.Problem;
import com.maersk.knapsack.jpa.entities.Task;

public interface KnapsackService {
	
	public Task submitTaskAndTriggerScheduler(Problem request);
	
}
