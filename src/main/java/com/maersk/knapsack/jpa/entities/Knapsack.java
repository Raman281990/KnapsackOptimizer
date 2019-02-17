package com.maersk.knapsack.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="knapsack")
public class Knapsack {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="knapsack_id")
	private Integer knapsackId;
	
	@Column(name="capacity")
	private Integer capacity;
	
	@Column(name="weights")
	private String weights;
	
	@Column(name="weight_values")
	private String values;
	
	@Column(name="solution")
	private String solution;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="task_id")
	private Task task;

	public Integer getKnapsackId() {
		return knapsackId;
	}

	public void setKnapsackId(Integer knapsackId) {
		this.knapsackId = knapsackId;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getWeights() {
		return weights;
	}

	public void setWeights(String weights) {
		this.weights = weights;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
}
