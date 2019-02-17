package com.maersk.knapsack.controller;

import javax.validation.Valid;


import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maersk.knapsack.dto.SolutionResponse;
import com.maersk.knapsack.dto.TaskInfo;
import com.maersk.knapsack.dto.TaskRequest;

import io.swagger.annotations.ApiOperation;

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
@RefreshScope
@RestController
public interface KnapsackOptimizerController {

	@ApiOperation(value="Submit a new Task for Optimization"
			,notes="This operation is used to submit a task " +
			"for knapsack optimization")
	@RequestMapping(value = "/tasks", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskInfo> submitTaskForOptimization(
			@RequestBody @Valid TaskRequest taskRequest ,BindingResult bindingResult); 
	
	@ApiOperation(value="Check the status the submitted task"
			,notes="This operation is used to check status for the task , " +
			"for knapsack optimization")
	@RequestMapping(value = "/tasks/{taskId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TaskInfo> checkStatus(@PathVariable String taskId);
	
	@ApiOperation(value="Fetch Knapsack Solution"
			,notes="This operation is used to fetch Solution , " +
			"generated by knapsack optimization")
	@RequestMapping(value = "/solutions/{taskId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SolutionResponse> getKnapsackSolution(@PathVariable String taskId);

}
