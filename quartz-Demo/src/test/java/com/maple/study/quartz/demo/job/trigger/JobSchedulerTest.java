package com.maple.study.quartz.demo.job.trigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.maple.study.quartz.demo.job.HelloJob;

public class JobSchedulerTest {
	
	public static void main(String[] args) throws SchedulerException {
//		new JobTriggerTest().testSimpleSchedule();
		new JobSchedulerTest().testCronSchedule();
	}

	public void testSimpleSchedule() throws SchedulerException {
		JobDetail job = JobBuilder.newJob(HelloJob.class)
				.withIdentity("helloJob", "group1")
				.build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("helloTrigger", "group1")
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInSeconds(5)
						.repeatForever()
						)
				.build();
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}
	
	public void testCronSchedule() throws SchedulerException {
		JobDetail job = JobBuilder.newJob(HelloJob.class)
				.withIdentity("helloJob", "group1")
				.build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("helloTrigger", "group1")
				.withSchedule(
						CronScheduleBuilder.cronSchedule("*/5 * * * * ?")
						)
				.build();
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);
	}
}
