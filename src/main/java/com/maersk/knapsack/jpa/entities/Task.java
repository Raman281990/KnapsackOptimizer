package com.maersk.knapsack.jpa.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="tasks")
public class Task {
	
	@Id
    @GenericGenerator(
        name = "task-sequence",
        strategy = "com.maersk.knapsack.config.TaskSequenceGenerator",
        parameters = {
            @org.hibernate.annotations.Parameter(
                name = "sequence_name", value = "hibernate_sequence"),
        }
    )
    @GeneratedValue(
        generator = "task-sequence",
        strategy = GenerationType.SEQUENCE)
	@Column(name="task_id")
	private String taskId;
	
	@Column(name="task_status")
	private String taskStatus;
	
	@Column(name="task_submitted_time")
	private Long taskSubmittedTime;
	
	@Column(name="task_started_time")
	private Long taskStartedTime;
	
	@Column(name="task_completed_time")
	private Long taskCompletedTime;
	
	@OneToOne(mappedBy="task", cascade = CascadeType.ALL)
	private Knapsack knapsack;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Long getTaskSubmittedTime() {
		return taskSubmittedTime;
	}

	public void setTaskSubmittedTime(Long taskSubmittedTime) {
		this.taskSubmittedTime = taskSubmittedTime;
	}

	public Long getTaskStartedTime() {
		return taskStartedTime;
	}

	public void setTaskStartedTime(Long taskStartedTime) {
		this.taskStartedTime = taskStartedTime;
	}

	public Long getTaskCompletedTime() {
		return taskCompletedTime;
	}

	public void setTaskCompletedTime(Long taskCompletedTime) {
		this.taskCompletedTime = taskCompletedTime;
	}

	public Knapsack getKnapsack() {
		return knapsack;
	}

	public void setKnapsack(Knapsack knapsack) {
		this.knapsack = knapsack;
	}
	
	public enum Status {
        SUBMITTED("submitted"),
        STARTED("started"), 
        COMPLETED("completed"),
        FAILED("failed");

        String value;

        Status(String status) {
            this.value = status;
        }

        public String getValue() {
            return value;
        }
    }
	

}
