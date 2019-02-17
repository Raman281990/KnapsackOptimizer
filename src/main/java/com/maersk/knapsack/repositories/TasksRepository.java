package com.maersk.knapsack.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.maersk.knapsack.jpa.entities.Task;

public interface TasksRepository  extends CrudRepository<Task, String> {
	
	@Query(" SELECT t FROM Task t \r\n" +
			 "	where taskStatus = :taskStatus order by task_submitted_time "
			 ) 
	public List<Task> getTasks(@Param("taskStatus") String taskStatus);
	
	@Modifying
	@Query(" Update Task  \r\n" +
			 "	set taskStatus = :taskStatus, taskStartedTime = :startedTime where taskId in (:list) "
			 ) 
	public int updateTaskStatus(@Param("taskStatus") String taskStatus,@Param("startedTime") Long startedTime,
			@Param("list") List<String> ids);
	
	
}
