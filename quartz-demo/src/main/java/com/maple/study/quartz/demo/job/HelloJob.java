package com.maple.study.quartz.demo.job;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

@DisallowConcurrentExecution
public class HelloJob implements Job {
	
	public HelloJob() {
		super();
	}

	public void execute(JobExecutionContext context) {
		System.out.println(String.format("[%s] - [%s] Hello, Begin to execute.", new Date(), context.getFireInstanceId()));
		try {
			Thread.sleep(6000);
			System.out.println(String.format("[%s] - [%s] Hello, End to execute.", new Date(), context.getFireInstanceId()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
