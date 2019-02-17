package com.maersk.knapsack.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.maersk.knapsack.dto.TaskInfo;
import com.maersk.knapsack.jpa.entities.Task;
import com.maersk.knapsack.service.KnapsackSolverService;


/**KnapsackSolverServiceImpl class contains the logic for Knapsack Optimizer
 * 
 * It provides the optimized solution for the knapsack Problem.
 * 
 * 
 * @author Ramandeep kaur
 *
 */
@Service
public class KnapsackSolverServiceImpl implements KnapsackSolverService {

	/* (non-Javadoc)
	 * solve methods provides optimized solution for the Knapsack Problem
	 * 
	 * @see com.maersk.knapsack.service.KnapsackSolverService#solve(com.maersk.knapsack.jpa.entities.Task)
	 */
	@Override
	public int[] solve(Task task) {
		int[] values =TaskInfo.getValues(task);
		int[] weights =TaskInfo.getWeights(task);
		if (weights.length != values.length)
			return null;

		int height = weights.length + 1; // weights==values
		int width = task.getKnapsack().getCapacity() + 1;
		int[][] output = new int[height][width];
		for (int i = 1; i < height; i++) {
			int index = i - 1;
			for (int j = 1; j < width; j++) {
				if (i == 0 || j == 0) {
					output[i][j] = 0;
				} else {
					if (weights[index] > j) {
						output[i][j] = output[i - 1][j];
					} else {
						int v = values[index] + output[i - 1][j - weights[index]];
						output[i][j] = Math.max(output[i - 1][j], v);
					}
				}
			}
		}

		final List<Integer> list = new ArrayList<Integer>();
		int i = height - 1;
		int j = width - 1;
		while (i != 0 && j != 0) {
			int current = output[i][j];
			int above = output[i - 1][j];
			if (current == above) {
				i -= 1;
			} else {
				i -= 1;
				j -= weights[i];
				list.add(i);
			}
		}
		Collections.sort(list);
		int count = 0;
		int[] result = new int[list.size()];
		for (Object obj : list.toArray()) {
			if (obj instanceof Integer)
				result[count++] = (Integer) obj;
		}

		return result;
	}

}
