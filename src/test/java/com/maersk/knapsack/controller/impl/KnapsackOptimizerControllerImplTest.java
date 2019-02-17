package com.maersk.knapsack.controller.impl;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.maersk.knapsack.dto.Problem;
import com.maersk.knapsack.dto.Solution;
import com.maersk.knapsack.dto.SolutionResponse;
import com.maersk.knapsack.dto.TaskInfo;
import com.maersk.knapsack.dto.TaskRequest;
import com.maersk.knapsack.jpa.entities.Knapsack;
import com.maersk.knapsack.jpa.entities.Task;
import com.maersk.knapsack.jpa.entities.Task.Status;
import com.maersk.knapsack.service.KnapsackService;
import com.maersk.knapsack.service.TaskService;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class KnapsackOptimizerControllerImplTest {
	
	@InjectMocks
	KnapsackOptimizerControllerImpl knapsackOptimizerControllerImpl;
	
	@Mock
	KnapsackService knapsackService;
	
	@Mock
	TaskService taskService;
	
	private static Problem problem ;
	
	private static Task task;
	
	
	@BeforeClass
	public static void setUpData() {
		problem = new Problem();
		problem.setCapacity(60);
		problem.setValues(Arrays.asList(10, 3, 30));
		problem.setWeights(Arrays.asList(10, 20, 33));
		task = new Task();
		task.setTaskId("ABC_123");
		task.setTaskStatus(Status.SUBMITTED.getValue());
		task.setTaskSubmittedTime(System.currentTimeMillis()/1000L);
		Knapsack knapsack =Problem.createKnapsackProblem(problem);
		task.setKnapsack(knapsack);
	}
	
	@Test
	public void testSubmitTaskForOptimization() {
		
	
		Mockito.when(knapsackService.submitTaskAndTriggerScheduler(Mockito.any(Problem.class))).thenReturn(task);
		
		BindingResult bindingResult = Mockito.mock(BindingResult.class);
		TaskRequest taskRequest = new TaskRequest();
		
		taskRequest.setProblem(problem);
		ResponseEntity<TaskInfo> response =knapsackOptimizerControllerImpl.submitTaskForOptimization(taskRequest, bindingResult);
		Assert.assertNotNull(response);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assert.assertEquals(Status.SUBMITTED.getValue(), response.getBody().getStatus());
	}
	
	@Test
	public void testForInvalidRequest() {
		
		BindingResult bindingResult = Mockito.mock(BindingResult.class);
		ObjectError errors = new ObjectError("Problem", "Invalid Request");
		
		bindingResult.addError(errors);
		Mockito.when(bindingResult.hasErrors()).thenReturn(true);
		TaskRequest taskRequest = new TaskRequest();
		
		taskRequest.setProblem(problem);
		ResponseEntity<TaskInfo> response =knapsackOptimizerControllerImpl.submitTaskForOptimization(taskRequest, bindingResult);
		Assert.assertNotNull(response);
		Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
	}
	
	@Test
	public void testCheckStatus() {
		
		Mockito.when(taskService.getTaskById(Mockito.anyString())).thenReturn(task);
	
		ResponseEntity<TaskInfo> response = knapsackOptimizerControllerImpl.checkStatus("ABC_123");
		Assert.assertNotNull(response);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals("ABC_123", response.getBody().getTask());
		Assert.assertEquals(Status.SUBMITTED.getValue(), response.getBody().getStatus());
	}
	
	@Test
	public void testCheckStatusForInvalidTaskId() {
		
		Mockito.when(taskService.getTaskById(Mockito.anyString())).thenReturn(null);
	
		ResponseEntity<TaskInfo> response = knapsackOptimizerControllerImpl.checkStatus("C_123");
		Assert.assertNotNull(response);
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
	}
	
	@Test
	public void testForUnavailableSolution() {
		Mockito.when(taskService.getTaskById(Mockito.anyString())).thenReturn(task);
		ResponseEntity<SolutionResponse> response = knapsackOptimizerControllerImpl.getKnapsackSolution("ABC_123");
		
		Assert.assertNotNull(response);
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
	}
	
	@Test
	public void testGetKnapsackSolution() {
		
		Problem problem = new Problem();
		problem.setCapacity(60);
		problem.setValues(Arrays.asList(10, 3, 30));
		problem.setWeights(Arrays.asList(10, 20, 33));
		Task completedTask = new Task();
		completedTask.setTaskId("ABC_123");
		completedTask.setTaskStatus(Status.COMPLETED.getValue());
		completedTask.setTaskSubmittedTime(System.currentTimeMillis()/1000L);
		completedTask.setTaskCompletedTime(System.currentTimeMillis()/1000L+10L);
		Knapsack knapsack =Problem.createKnapsackProblem(problem);
		knapsack.setSolution("0,2");
		completedTask.setKnapsack(knapsack);
		Solution expected = Solution.getKnapsackSolution(completedTask);
		
		Mockito.when(taskService.getTaskById(Mockito.anyString())).thenReturn(completedTask);
		ResponseEntity<SolutionResponse> response = knapsackOptimizerControllerImpl.getKnapsackSolution("ABC_123");
		Assert.assertNotNull(response);
		Assert.assertEquals(expected.getItems().size(), response.getBody().getSolution().getItems().size());
	}
	
	@Test
	public void testGetKnapsackSolutionForInvalidTaskId() {
		
		Mockito.when(taskService.getTaskById(Mockito.anyString())).thenReturn(null);
		ResponseEntity<SolutionResponse> response = knapsackOptimizerControllerImpl.getKnapsackSolution("A_123");
		Assert.assertNotNull(response);
		Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

}
