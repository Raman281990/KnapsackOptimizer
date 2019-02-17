package com.maersk.knapsack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maersk.knapsack.dto.Solution;
import com.maersk.knapsack.jpa.entities.Task;
import com.maersk.knapsack.repositories.TasksRepository;
import com.maersk.knapsack.service.KnapsackSolverService;
import com.maersk.knapsack.service.TaskProcessingService;

/** TaskProcessingServiceImpl service processes the submitted task in the system,
 *  calls the Knapsack Solver for solving the problem and saving the optimized solution in the
 *  database and mark the task as completed.
 * 
 * @author Ramandeep kaur
 *
 */
@Service
public class TaskProcessingServiceImpl implements TaskProcessingService {

	@Autowired
	KnapsackSolverService knapsackSolverService;
	
	@Autowired
	TasksRepository tasksRepository;
	
	/** 
	 * processTasks method calls the KnapsackSolver Service for each task to obtain the
	 * optimized solution and persist the solution in database and mark the task as completed. 
	 * 
	 * @param task
	 * @return task
	 */
	@Override
	public Task processTasks(Task task) {
		int[] solution =knapsackSolverService.solve(task);
		if(solution!= null) {
			task =persistTaskAndSolution(task, solution);
		}
		return task;
	}

	/**persistTaskAndSolution persist the task and solution in the database
	 * 
	 * @param task
	 * @param solution
	 * @return
	 */
	private Task persistTaskAndSolution(Task task, int[] solution) {
		task = Solution.updateSolutionAndStatus(task, solution);
		tasksRepository.save(task);
		return task;
	}

}
