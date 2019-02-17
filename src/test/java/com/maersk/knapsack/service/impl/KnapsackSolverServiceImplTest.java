package com.maersk.knapsack.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.maersk.knapsack.jpa.entities.Knapsack;
import com.maersk.knapsack.jpa.entities.Task;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class KnapsackSolverServiceImplTest {
	
	@InjectMocks
	KnapsackSolverServiceImpl knapsackSolverServiceImpl;
	
	@Test
	public void testSolveKnapsackProblem() {
		
		Task task = new Task();
		Knapsack knapsack=new Knapsack();
		knapsack.setCapacity(15);
		knapsack.setKnapsackId(1);
		knapsack.setValues("4,2,2,1,10");
		knapsack.setWeights("12,1,2,1,4");
		task.setKnapsack(knapsack);
		int[] solution = knapsackSolverServiceImpl.solve(task);
		Assert.assertNotNull(solution);
		Assert.assertEquals(4, solution.length);
		Assert.assertEquals(4, solution[3]);
	}

}
