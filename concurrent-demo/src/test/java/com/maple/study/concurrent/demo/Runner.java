package com.maple.study.concurrent.demo;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Runner implements Runnable {

	private CyclicBarrier barrier;
	private String name;
	
	public Runner(CyclicBarrier barrier, String name) {
		super();
		this.barrier = barrier;
		this.name = name;
	}

	public static void main(String[] args) throws IOException, InterruptedException {  
        CyclicBarrier barrier = new CyclicBarrier(3);  
  
        ExecutorService executor = Executors.newFixedThreadPool(3);  
        executor.submit(new Thread(new Runner(barrier, "zhangsan")));  
        executor.submit(new Thread(new Runner(barrier, "lisi")));  
        executor.submit(new Thread(new Runner(barrier, "wangwu")));  
  
        executor.shutdown();  
    }  


	@Override
	public void run() {
		try {
			Thread.sleep(1000 * new Random().nextInt(8));
			System.out.println(name + " is ready.");
			barrier.await();
		} catch (InterruptedException e) {
		} catch (BrokenBarrierException e) {
		}
		System.out.println(name + " Go!!");
	}

}
