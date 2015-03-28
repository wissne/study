package com.maple.study.concurrent.demo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	public static void main(String[] args) {
		BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
		ExecutorService executor = new ThreadPoolExecutor(3, 6, 1, TimeUnit.DAYS, queue);
		
		for (int i = 0; i < 20; i++) {
			final int index = i + 1;
			executor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println(String.format("Thread %s started", index));
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Thread " + index + " is finished.");
				}
			});
		}
		
		executor.shutdown();
	}
}
