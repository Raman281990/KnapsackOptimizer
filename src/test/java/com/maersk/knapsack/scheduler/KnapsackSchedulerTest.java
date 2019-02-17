package com.maersk.knapsack.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.maersk.knapsack.jpa.entities.Task;
import com.maersk.knapsack.jpa.entities.Task.Status;
import com.maersk.knapsack.service.TaskProcessingService;
import com.maersk.knapsack.service.TaskService;

@RunWith(MockitoJUnitRunner.class)
public class KnapsackSchedulerTest {
	
	@InjectMocks
	KnapsackScheduler knapsackScheduler;
	
	@Mock
	TaskService taskService;

	@Mock
	TaskProcessingService taskProcessingService;
	
	@Test
	public void testProcessSubmittedTask() {
		List<Task> tasks = new ArrayList();
		Task task1 = new Task();
		task1.setTaskId("ABC_123");
		task1.setTaskStatus(Status.SUBMITTED.getValue());
		tasks.add(task1);
		Mockito.when(taskService.getAllSubmittedTasks()).thenReturn(Optional.ofNullable(tasks));
		Mockito.when(taskProcessingService.processTasks(Mockito.any(Task.class))).thenReturn(task1);
		knapsackScheduler.processSubmittedTask();
	}
	

}
