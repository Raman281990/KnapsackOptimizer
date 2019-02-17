package com.maersk.knapsack.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maersk.knapsack.jpa.entities.Task;
import com.maersk.knapsack.repositories.TasksRepository;
import com.maersk.knapsack.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService{
	
	private static final String SUBMITTED_TASK="submitted";
	private static final String STARTED_TASK="started";

	@Autowired
	TasksRepository tasksRepository;
	
	/* (non-Javadoc)getAllSubmittedTasks method fetches all the submitted Task
	 * int the systm
	 * @see com.maersk.knapsack.service.TaskService#getAllSubmittedTasks()
	 */
	@Transactional
	@Override
	public Optional<List<Task>> getAllSubmittedTasks() {
		return Optional.ofNullable(tasksRepository.getTasks(SUBMITTED_TASK));
	}
	/* (non-Javadoc)markAsStarted method marks the Task to started and updates the
	 * started time of the task.
	 * 
	 * @see com.maersk.knapsack.service.TaskService#markTaskStatusToStarted(java.util.List)
	 */
	@Transactional
	@Override
	public int markAsStarted(List<Task> tasks) {
		List<String> ids = tasks.stream().map(task -> task.getTaskId()).collect(Collectors.toList());
		Long startedTime = System.currentTimeMillis() / 1000L;
		tasks.stream().forEach(task -> {
			task.setTaskStartedTime(startedTime);
		});
		return tasksRepository.updateTaskStatus(STARTED_TASK, startedTime,ids);
	
	}
	/* (non-Javadoc)saveTasks persists the Task in database
	 * 
	 * @see com.maersk.knapsack.service.TaskService#saveTasks(com.maersk.knapsack.jpa.entities.Task)
	 */
	@Override
	public void saveTasks(Task task) {
		tasksRepository.save(task);
	}
	

	/** getTaskById fetches the  Task from database based on the ID
	 * 
	 * @param String taskId
	 * @return Task
	 */
	@Override
	public Task getTaskById(String taskId) {
		return tasksRepository.findOne(taskId);
	
	}

	/** getAllTasks method fetches all the Tasks(Started/Submitted/completed)
	 *  from the database.
	 * 
	 * @return List<Task>
	 */
	@Override
	public Optional<List<Task>> getAllTasks() {
		return Optional.ofNullable((List<Task>)tasksRepository.findAll());
		
	}


}
