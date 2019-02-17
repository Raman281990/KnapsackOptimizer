package com.maersk.knapsack.config;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.maersk.knapsack.scheduler.KnapsackScheduler;
import com.maersk.knapsack.scheduler.SchedulerObjectInterface;

@Configuration
public class SchedulerConfig implements SchedulingConfigurer , SchedulerObjectInterface{

	
	private ScheduledFuture<?> future;

	
	private TaskScheduler scheduler;
	
	@Value("${scheduler.timer}")
	private Long timer;
	

	@Bean
	public KnapsackScheduler schedulerBean() {
		return new KnapsackScheduler();
	}

	@Override
	public void start() {
		
		future = scheduler.schedule(new Runnable() {
			@Override
			public void run() {
				schedulerBean().processSubmittedTask();
			}
		}, new Date( System.currentTimeMillis() + timer) );

	}


	@Override
	public void stop() {
		future.cancel(true);

	}
	
	@Bean(name = "executorService") 
    ExecutorService taskExecutors() { 
		ThreadPoolTaskScheduler threadPoolTaskScheduler =
				new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(100);
		threadPoolTaskScheduler.setThreadNamePrefix("knapsack-scheduled-task-pool-");
		threadPoolTaskScheduler.initialize();
		threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
		scheduler=threadPoolTaskScheduler;
		return threadPoolTaskScheduler.getScheduledThreadPoolExecutor();
    } 

    @Override 
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) { 
     taskRegistrar.setScheduler(taskExecutors()); 
    } 

}