package com.maersk.knapsack.controller.impl;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.maersk.knapsack.controller.KnapsackOptimizerController;
import com.maersk.knapsack.dto.Error;
import com.maersk.knapsack.dto.Problem;
import com.maersk.knapsack.dto.Solution;
import com.maersk.knapsack.dto.SolutionResponse;
import com.maersk.knapsack.dto.TaskInfo;
import com.maersk.knapsack.dto.TaskRequest;
import com.maersk.knapsack.jpa.entities.Task;
import com.maersk.knapsack.jpa.entities.Task.Status;
import com.maersk.knapsack.service.KnapsackService;
import com.maersk.knapsack.service.TaskService;

/**KnapsackOptimizerControllerImpl contains the API endpoints for the Knapsack  Optimizer.
 * It contains the endpoints for 
 * 
 * 1) submit Task 
 * 2) Check the status of the Task
 * 3) Fetch the Solution of the Task
 * 
 * @author Ramandeep kaur
 *
 */
@Component
public class KnapsackOptimizerControllerImpl implements KnapsackOptimizerController{

	private static final Logger LOGGER = LoggerFactory.getLogger(KnapsackOptimizerController.class);

	@Autowired
	KnapsackService knapsackService;
	
	@Autowired
	TaskService taskService;

	/* (non-Javadoc)
	 * @see com.maersk.knapsack.controller.KnapsackOptimizerController#submitTaskForOptimization(com.maersk.knapsack.vo.TaskRequest, org.springframework.validation.BindingResult)
	 */
	@Override
	public ResponseEntity<TaskInfo> submitTaskForOptimization(
			@RequestBody @Valid TaskRequest request , BindingResult bindingResult) {
		
		TaskInfo taskInfo = new TaskInfo();
		if(!bindingResult.hasErrors()) {
			LOGGER.info(":::Submitting Task with Values--->>"+request.getProblem()); 
			Task task =knapsackService.submitTaskAndTriggerScheduler(request.getProblem());
			taskInfo = TaskInfo.mapTaskSubmissionResponse(task);
			return new ResponseEntity<TaskInfo>(taskInfo,HttpStatus.CREATED);

		} 
		mapErrorMessage(bindingResult, taskInfo);
		LOGGER.info(":::Submitted Request is Invalid:::"); 
		return new ResponseEntity<TaskInfo>(taskInfo,HttpStatus.BAD_REQUEST);

	}


	/* (non-Javadoc)
	 * @see com.maersk.knapsack.controller.KnapsackOptimizerController#checkStatus(java.lang.String)
	 */
	@Override
	public ResponseEntity<TaskInfo> checkStatus(@PathVariable String taskId) {
		LOGGER.debug("Checking status of Task with id {}", taskId);
		Task task = taskService.getTaskById(taskId);
		if (task == null) {
			LOGGER.debug("Task with id {} not found", taskId);
			return new ResponseEntity<TaskInfo>(HttpStatus.NOT_FOUND);
		}
		TaskInfo response  = TaskInfo.taskResponseMapper(task);
		return new ResponseEntity<TaskInfo>(response, HttpStatus.OK);
	}
	
	/* (non-Javadoc)
	 * @see com.maersk.knapsack.controller.KnapsackOptimizerController#getKnapsackSolution(java.lang.String)
	 */
	@Override
    public ResponseEntity<SolutionResponse> getKnapsackSolution(@PathVariable String taskId) {
        LOGGER.debug("Checking status of Task with id {}", taskId);
        Task task = taskService.getTaskById(taskId);
        if (task == null) {
            LOGGER.debug("Task with id {} not found", taskId);
            return new ResponseEntity<SolutionResponse>(HttpStatus.NO_CONTENT);
        }
        SolutionResponse solutionResponse = null;
        if(Status.COMPLETED.getValue().equalsIgnoreCase(task.getTaskStatus())) {
            Problem problem = Problem.getKnapsackProblem(task);
            Solution response =  Solution.getKnapsackSolution(task);
            solutionResponse = new SolutionResponse(taskId,problem,response);
        } else {
            LOGGER.debug("Solution is not available for Task {} ", taskId);
            return new ResponseEntity<SolutionResponse>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<SolutionResponse>(solutionResponse, HttpStatus.OK);
    }

	private void mapErrorMessage(BindingResult bindingResult, TaskInfo taskInfo) {
		for(ObjectError er  : bindingResult.getAllErrors()) {
			Error err = new Error(er.getDefaultMessage());
			taskInfo.setError(err);
		}
	}
}
