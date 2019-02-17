package com.maersk.knapsack.scheduler;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.maersk.knapsack.jpa.entities.Task;
import com.maersk.knapsack.service.TaskProcessingService;
import com.maersk.knapsack.service.TaskService;

/**KnapsackScheduler class received trigger when Tasks are submitted to
 * 	the Knapsack Optimizer Application.
 * 
 * This scheduler picks up all the submitted task, marks them to started status 
 * and sends them to TaskprocessingService for asynchronous processing the tasks.
 * 
 * @author Ramandeep kaur
 *
 */
@Component
public class KnapsackScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(KnapsackScheduler.class);

	@Autowired
	TaskService taskService;

	@Autowired
	TaskProcessingService taskProcessingService;


	/** processSubmittedTask methods picks the submitted tasks in the application
	 * marks them started and  process these records 
	 * 
	 * @author Ramandeep kaur
	 */
	public void processSubmittedTask() {
		LOGGER.info(":::Scheduler started for submitted Task:::");
		Optional<List<Task>> taskList = taskService.getAllSubmittedTasks();
		if(taskList.isPresent()) {
			List<Task> tasks = taskList.get();
			taskService.markAsStarted(tasks);
			process(tasks);
		}

	}
	/**process method process the problem and submits the task to TaskProcessing service for asynchronous processing
	 * of the knapsack problem
	 * 
	 * @param taskList
	 */
	@Async
	private void process(List<Task> taskList) {
		taskList.stream().forEach(task -> CompletableFuture.supplyAsync(() -> taskProcessingService.processTasks(task)));
	}

}
