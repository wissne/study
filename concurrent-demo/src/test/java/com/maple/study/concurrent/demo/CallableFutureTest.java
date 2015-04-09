package com.maple.study.concurrent.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CallableFutureTest {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		oneSubThread();
		System.out.println("--------------------------------------------");
		multiThread();  
	}

	private static void multiThread() throws InterruptedException,
			ExecutionException {
		System.out.println("start main thread");  
        int threadCount = 5;  
        final ExecutorService exec = Executors.newFixedThreadPool(threadCount);  
        
        List<Future<Integer>> tasks = new ArrayList<Future<Integer>>(); 
        for (int i = 0; i < 10; i++) {  
        	final int NO = i + 1;
            Callable<Integer> call = new Callable<Integer>() {
                public Integer call() throws Exception {  
                    Thread.sleep(1000);  
                    System.out.println(String.format("No. %d is finished", NO));
                    return 1;  
                }  
            };  
            tasks.add(exec.submit(call));  
        }  

        long total = 0;  
        for (Future<Integer> future : tasks) {
            total += future.get();  
        }  
        
        exec.shutdown();  
        System.out.println("total: " + total);  
        System.out.println("end main thread");
	}

	private static void oneSubThread() throws InterruptedException,
			ExecutionException {
		System.out.println("Start main thread");
		
		final ExecutorService exec = Executors.newFixedThreadPool(5);
		
		Callable<String> call = new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				System.out.println("  Start new thread");
				Thread.sleep(3000);
				System.out.println("  End new thread");
				return "some value";
			}
		};
		
		Future<String> task = exec.submit(call);
		Thread.sleep(2000);
		
		System.out.println(String.format("task.get() : [%s]", task.get()));;
		
		exec.shutdown();
		exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		System.out.println("End main thread");
	}
	
}
