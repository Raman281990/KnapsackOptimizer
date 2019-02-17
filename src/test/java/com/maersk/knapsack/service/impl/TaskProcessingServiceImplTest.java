package com.maersk.knapsack.service.impl;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.maersk.knapsack.dto.Problem;
import com.maersk.knapsack.jpa.entities.Knapsack;
import com.maersk.knapsack.jpa.entities.Task;
import com.maersk.knapsack.jpa.entities.Task.Status;
import com.maersk.knapsack.repositories.TasksRepository;
import com.maersk.knapsack.service.KnapsackSolverService;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class TaskProcessingServiceImplTest {

	@InjectMocks
	TaskProcessingServiceImpl taskProcessingServiceImpl;
	
	@Mock
	KnapsackSolverService knapsackSolverService;
	
	@Mock
	TasksRepository tasksRepository;
	
	@Test
	public void testProcessTasks() {
		
		int[] solution = {0,2};
		Mockito.when(knapsackSolverService.solve(Mockito.any(Task.class))).thenReturn(solution);
		
		
		Problem problem = new Problem();
		problem.setCapacity(60);
		problem.setValues(Arrays.asList(10, 3, 30));
		problem.setWeights(Arrays.asList(10, 20, 33));
		Task task = new Task();
		task.setTaskId("ABC_123");
		task.setTaskStatus(Status.STARTED.getValue());
		task.setTaskSubmittedTime(System.currentTimeMillis()/1000L);
		task.setTaskStartedTime(System.currentTimeMillis()/1000L+30000L);
		Knapsack knapsack =Problem.createKnapsackProblem(problem);
		task.setKnapsack(knapsack);
		Task response = taskProcessingServiceImpl.processTasks(task);
		
		Assert.assertNotNull(response);
		Assert.assertEquals("0,2", response.getKnapsack().getSolution());
		
		
	}
	
	
}
