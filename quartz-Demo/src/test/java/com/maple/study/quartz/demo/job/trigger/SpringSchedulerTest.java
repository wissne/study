package com.maple.study.quartz.demo.job.trigger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringSchedulerTest {

	public static void main(String[] args) {
		System.out.println("Test begin...");
		ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		System.out.println("Test end...");
	}

}
