package com.maersk.knapsack.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maersk.knapsack.dto.Problem;
import com.maersk.knapsack.dto.TaskInfo;
import com.maersk.knapsack.jpa.entities.Knapsack;
import com.maersk.knapsack.jpa.entities.Task;
import com.maersk.knapsack.repositories.TasksRepository;
import com.maersk.knapsack.scheduler.SchedulerObjectInterface;
import com.maersk.knapsack.service.KnapsackService;

/**KnapsackServiceImpl provide various services for Knapsack Task submitted in the system'
 * 
 * @author Ramandeep kaur
 *
 */
@Service
public class KnapsackServiceImpl implements KnapsackService {
	
	@Autowired
	TasksRepository tasksRepository;
	
	@Autowired
	SchedulerObjectInterface schedulerObjectInterface;

	/** submitTaskAndTriggerScheduler method is used to create a Task object corresponding to the problem submitted
	 * by the user and persist the Task in the database
	 * 
	 * 1) Persist the Task in database
	 * 2) Triggers the scheduler for processing submitted task in the system.
	 * 
	 * @author Ramandeep kaur
	 *
	 */
	@Override
	public Task submitTaskAndTriggerScheduler(Problem request) {
		
		Knapsack knapsack = Problem.createKnapsackProblem(request);		
		Task task = persistTaskAndKnapsackProblem(knapsack);
		//trigger to the scheduler for processing the submitted Tasks.
		schedulerObjectInterface.start();
		return task;
		
	}


	/**persistTaskAndKnapsackProblem creates a Task entity and persist it in the database
	 * 
	 * @param knapsack
	 * @return Task
	 */
	private Task persistTaskAndKnapsackProblem(Knapsack knapsack) {
		Task tasks = TaskInfo.createTask();
		tasks.setKnapsack(knapsack);
		knapsack.setTask(tasks);
		tasksRepository.save(tasks);
		return tasks;
	}



	

}
