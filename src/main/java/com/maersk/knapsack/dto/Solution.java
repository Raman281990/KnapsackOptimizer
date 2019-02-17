package com.maersk.knapsack.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maersk.knapsack.jpa.entities.Knapsack;
import com.maersk.knapsack.jpa.entities.Task;
import com.maersk.knapsack.jpa.entities.Task.Status;

public class Solution {

	@JsonProperty("items")
	private List<Integer> items;

	@JsonProperty("time")
	private Long time;

	public List<Integer> getItems() {
		return items;
	}

	public void setItems(List<Integer> items) {
		this.items = items;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public static Solution getKnapsackSolution(Task task) {
		Solution solution = new Solution();
		List<Integer> items = Stream.of(task.getKnapsack().getSolution().split(",")).map(Integer::valueOf).collect(Collectors.toList());
		solution.setItems(items);
		solution.setTime(task.getTaskCompletedTime() - task.getTaskSubmittedTime());
		return solution;

	}
	public static String createSolution(int[] solution) {
		return IntStream.of(solution)
				.mapToObj(String::valueOf).collect(Collectors.joining(","));
	}
	public static Task updateSolutionAndStatus(Task task, int[] solution) {
		if(solution.length>0) {
			task.setTaskStatus(Status.COMPLETED.getValue());
			task.setTaskCompletedTime(System.currentTimeMillis() / 1000L);
			Knapsack knapsack  = task.getKnapsack();
			String result =Solution.createSolution(solution);
			knapsack.setSolution(result);
		}
		else {
			task.setTaskStatus(Status.FAILED.getValue());
		}
		return task;
	}

}
