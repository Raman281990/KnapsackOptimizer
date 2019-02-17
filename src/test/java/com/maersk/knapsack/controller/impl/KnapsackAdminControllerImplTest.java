package com.maersk.knapsack.controller.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.maersk.knapsack.dto.AllTasks;
import com.maersk.knapsack.dto.Problem;
import com.maersk.knapsack.jpa.entities.Knapsack;
import com.maersk.knapsack.jpa.entities.Task;
import com.maersk.knapsack.jpa.entities.Task.Status;
import com.maersk.knapsack.service.TaskService;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class KnapsackAdminControllerImplTest {
	
	@InjectMocks
	KnapsackAdminControllerImpl knapsackAdminControllerImpl;
	
	@Mock
	TaskService taskService;
	
	@Test
	public void testFetchAllTasks() {
		
		List<Task> tasks = new ArrayList<>();
		Problem problem = new Problem();
		problem.setCapacity(60);
		problem.setValues(Arrays.asList(10, 3, 30));
		problem.setWeights(Arrays.asList(10, 20, 33));
		Task task1 = new Task();
		task1.setTaskId("ABC_123");
		task1.setTaskStatus(Status.SUBMITTED.getValue());
		task1.setTaskSubmittedTime(System.currentTimeMillis()/1000L);
		Knapsack knapsack =Problem.createKnapsackProblem(problem);
		task1.setKnapsack(knapsack);
		tasks.add(task1);
		
		
		Mockito.when(taskService.getAllTasks()).thenReturn(Optional.ofNullable(tasks));
		ResponseEntity<AllTasks> response = knapsackAdminControllerImpl.fetchTasks();
		
		Assert.assertNotNull(response);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertNotNull(response.getBody().getTasks().getSubmitted());
		
	}
	

	@Test
	public void testWhenNoTaskIsPresent() {
		
		Mockito.when(taskService.getAllTasks()).thenReturn(Optional.ofNullable(null));
		ResponseEntity<AllTasks> response = knapsackAdminControllerImpl.fetchTasks();
		
		Assert.assertNotNull(response);
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
	}
	

}
