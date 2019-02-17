package com.maersk.knapsack.service.impl;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.maersk.knapsack.dto.Problem;
import com.maersk.knapsack.jpa.entities.Knapsack;
import com.maersk.knapsack.jpa.entities.Task;
import com.maersk.knapsack.jpa.entities.Task.Status;
import com.maersk.knapsack.repositories.TasksRepository;
import com.maersk.knapsack.scheduler.SchedulerObjectInterface;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class KnapsackServiceImplTest {
	
	@InjectMocks
	KnapsackServiceImpl knapsackServiceImpl;
	
	@Mock
	TasksRepository tasksRepository;
	
	@Mock
	SchedulerObjectInterface schedulerObjectInterface;
	
	@Test
	public void testSubmitKnapsackOptimizationTask() {
		
		Mockito.doNothing().when(schedulerObjectInterface).start();
		Problem problem = new Problem();
		problem.setCapacity(60);
		problem.setValues(Arrays.asList(10, 3, 30));
		problem.setWeights(Arrays.asList(10, 20, 33));
		Task task = new Task();
		task.setTaskId("ABC_123");
		task.setTaskStatus(Status.SUBMITTED.getValue());
		task.setTaskSubmittedTime(System.currentTimeMillis()/1000L);
		Knapsack knapsack =Problem.createKnapsackProblem(problem);
		
		task.setKnapsack(knapsack);
		Mockito.when(tasksRepository.save(Mockito.any(Task.class))).thenReturn(task);
		
		
		Task response = knapsackServiceImpl.submitTaskAndTriggerScheduler(problem);
		
		Assert.assertNotNull(response);
		Assert.assertEquals(Status.SUBMITTED.getValue(), response.getTaskStatus());
		Assert.assertEquals(new Integer(60), response.getKnapsack().getCapacity());
	
		
	}
	

}
