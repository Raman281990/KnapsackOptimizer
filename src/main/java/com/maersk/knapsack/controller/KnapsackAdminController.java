package com.maersk.knapsack.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maersk.knapsack.dto.AllTasks;

import io.swagger.annotations.ApiOperation;

/** KnapsackAdminController is for admin use.
 * 1) Admin is able to retrive all the tasks(Submitted/Started/Completed) in the system
 * 
 * @author Ramandeep kaur
 *
 */
@RefreshScope
@RestController
public interface KnapsackAdminController {

	/**fetchTasks Operation is used to fetch all the Tasks present in the system(Submitted/Started/Completed)
	 * 
	 * @return AllTasks
	 */
	@ApiOperation(value="Fetches all the Tasks"
			,notes="This operation is used to fetch all the tasks, " +
			"  submitted to the knapsack optimization Application")
	@RequestMapping(value = "/admin/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AllTasks> fetchTasks();

	

	
}
