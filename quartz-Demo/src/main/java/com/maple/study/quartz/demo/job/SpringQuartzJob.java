package com.maple.study.quartz.demo.job;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

@DisallowConcurrentExecution
public class SpringQuartzJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println(String.format("[%s] - [%s-SpringQuartzJob] Hello, Begin to execute.", new Date(), context.getFireInstanceId()));
		try {
			Thread.sleep(6000);
			System.out.println(String.format("[%s] - [%s-SpringQuartzJob] Hello, End to execute.", new Date(), context.getFireInstanceId()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
