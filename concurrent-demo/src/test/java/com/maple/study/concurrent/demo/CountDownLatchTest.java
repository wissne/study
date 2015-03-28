package com.maple.study.concurrent.demo;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {

	public static void main(String[] args) throws InterruptedException {
		final CountDownLatch begin = new CountDownLatch(1);
		
		final CountDownLatch end = new CountDownLatch(10);
		
		final ExecutorService exec = Executors.newFixedThreadPool(10);
		
		for (int i = 0; i < 10; i++) {
			final int NO = i + 1;
			Runnable run = new Runnable() {
				
				@Override
				public void run() {
					try {
						begin.await();
						Thread.sleep(new Random().nextInt(5) * 1000);
						System.out.println(String.format("NO. : %s arrived", NO));
					} catch (Exception e) {
					} finally {
						end.countDown();
					}
				}
			};
			
			exec.execute(run);
		}
		
		System.out.println("Game Start");
		begin.countDown();
		end.await();
		System.out.println("Game Over");
		exec.shutdown();
	}

}
