package com.maersk.knapsack.service.impl;

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
import com.maersk.knapsack.repositories.TasksRepository;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {

	@InjectMocks
	TaskServiceImpl taskServiceImpl;

	@Mock
	TasksRepository tasksRepository;

	@Test
	public void testGetAllSubmittedTasks() {
		List<Task> tasks = new ArrayList();
		Task task1 = new Task();
		task1.setTaskId("ABC_123");
		task1.setTaskStatus(Status.SUBMITTED.getValue());
		tasks.add(task1);
		Mockito.when(tasksRepository.getTasks(Mockito.anyString())).thenReturn(tasks);

		Optional<List<Task>> response = taskServiceImpl.getAllSubmittedTasks();
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.get());
		Assert.assertEquals(1, response.get().size());

	}

	@Test
	public void testMarkAsStarted() {

		Mockito.when(tasksRepository.updateTaskStatus(Mockito.anyString(), Mockito.anyLong(),
				Mockito.anyListOf(String.class))).thenReturn(1);
		List<Task> tasks = new ArrayList();
		Task task1 = new Task();
		task1.setTaskId("ABC_123");
		task1.setTaskStatus(Status.SUBMITTED.getValue());
		tasks.add(task1);
		int response = taskServiceImpl.markAsStarted(tasks);
		
		Assert.assertNotNull(response);
		Assert.assertEquals(1, response);

	}
	
	@Test
	public void testSaveTasks() {
		Task task = new Task();
		task.setTaskId("ABC_123");
		task.setTaskStatus(Status.SUBMITTED.getValue());
		
		taskServiceImpl.saveTasks(task);
		
	}
	
	@Test
	public void testGetTaskById() {
		taskServiceImpl.getTaskById("ABC_123");
	}
	
	@Test
	public void testGetAllTasks() {
		List<Task> tasks = new ArrayList();
		Task task1 = new Task();
		task1.setTaskId("ABC_123");
		task1.setTaskStatus(Status.SUBMITTED.getValue());
		tasks.add(task1);
		
		Mockito.when(tasksRepository.findAll()).thenReturn(tasks);
		Optional<List<Task>> response = taskServiceImpl.getAllTasks();
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.get());
		Assert.assertEquals(1, response.get().size());
	}
	

}
