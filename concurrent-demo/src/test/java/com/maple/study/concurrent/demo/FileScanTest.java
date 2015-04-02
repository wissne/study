package com.maple.study.concurrent.demo;

import java.io.File;
import java.io.FileFilter;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class FileScanTest {
	
	public static long randomTime() {
		return (long) (new Random().nextInt(8) * 1000);
	}

	public static void main(String[] args) {
		final BlockingQueue<File> queue = new LinkedBlockingQueue<File>(100);
		
		final ExecutorService executor = Executors.newFixedThreadPool(5);
		
		final File root = new File("D:/GIT/Study");
		
		final File exitFile = new File("");
		
		final AtomicInteger rc = new AtomicInteger();
		
		final AtomicInteger wc = new AtomicInteger();
		
		Runnable read = new Runnable() {
			
			@Override
			public void run() {
				scanFile(root);
				scanFile(exitFile);
			}

			public void scanFile(File file) {
				if (file.isDirectory()) {
					File[] files = file.listFiles(new FileFilter() {
						@Override
						public boolean accept(File pathname) {
							return pathname.isDirectory() || pathname.getPath().endsWith(".java");
						}
					});
					for (File one : files) {
						scanFile(one);
					}
				} else {
					try {
						int index = rc.incrementAndGet();
						System.out.println(String.format("Read0 : %s - %s", index, file.getPath()));
						queue.put(file);
					} catch (Exception e) {
					}
				}
			}
		};
		
		executor.execute(read);
		
		for (int i = 0; i < 4; i++) {
			final int num = i;
			Runnable write = new Runnable() {
				
                String threadName = "Write" + num;  
  
                public void run() {  
                    while (true) {  
                        try {  
                            Thread.sleep(randomTime());
                            int index = wc.incrementAndGet();  
                            File file = queue.take();  
                            if (file == exitFile) {  
                                queue.put(exitFile);  
                                break;  
                            }  
                            System.out.println(threadName + ": " + index + " " + file.getPath());  
                        } catch (InterruptedException e) {  
                        }  
                    }  
	            };  
			};
			executor.submit(write);  
		}
		
		executor.shutdown();
	}
}
