package com.maple.study.quartz.demo.job;

import java.util.Date;

public class SpringJob {

	public void execute() {
		System.out.println(String.format("[%s] Hello Spring Quartz Job", new Date()));
	}
}
