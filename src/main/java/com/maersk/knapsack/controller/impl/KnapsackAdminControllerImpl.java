package com.maersk.knapsack.controller.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.maersk.knapsack.controller.KnapsackAdminController;
import com.maersk.knapsack.dto.AllTasks;
import com.maersk.knapsack.dto.KnapsackTasks;
import com.maersk.knapsack.dto.TaskInfo;
import com.maersk.knapsack.jpa.entities.Task;
import com.maersk.knapsack.service.TaskService;

/** KnapsackAdminController is for admin use.
 * 1) Admin is able to retrive all the tasks(Submitted/Started/Completed) in the system
 * 
 * @author Ramandeep kaur
 *
 */
@Component
public class KnapsackAdminControllerImpl implements KnapsackAdminController{

	@Autowired
	TaskService taskService;

	/**fetchTasks Operation is used to fetch all the Tasks present in the system(Submitted/Started/Completed)
	 * 
	 * @return AllTasks
	 */
	@Override
	public ResponseEntity<AllTasks> fetchTasks() {
		Optional<List<Task>> optionalList = taskService.getAllTasks();
		AllTasks allTasks = null;
		if(optionalList.isPresent()) {
			List<Task> tasks = optionalList.get();
			List<Task> submitted = TaskInfo.filterSubmittedTasks(tasks);
			List<Task> started = TaskInfo.filterStartedTasks(tasks);
			List<Task> completed = TaskInfo.filterCompletedTasks(tasks);
			allTasks = mapResponse(submitted, started, completed);

			return new ResponseEntity<AllTasks>(allTasks,HttpStatus.OK);
		}
		return new ResponseEntity<AllTasks>(allTasks,HttpStatus.NOT_FOUND);
	}

	private AllTasks mapResponse(List<Task> submitted, List<Task> started, List<Task> completed) {
		AllTasks allTasks = new AllTasks();
		KnapsackTasks knapsackTasks = new KnapsackTasks();
		knapsackTasks.addSubmittedTasks(TaskInfo.mapTasks(submitted));
		knapsackTasks.addStartedTasks(TaskInfo.mapTasks(started));
		knapsackTasks.addCompletedTasks(TaskInfo.mapTasks(completed));
		allTasks.addTasks(knapsackTasks);
		return allTasks;
	}

}
