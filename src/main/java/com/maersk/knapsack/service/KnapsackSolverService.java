package com.maersk.knapsack.service;

import com.maersk.knapsack.jpa.entities.Task;

public interface KnapsackSolverService {
	
	public int[] solve(Task task);

}
