package com.jdk.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Test;

public class LoggerTest {

	private static Logger logger = Logger.getLogger(LoggerTest.class.getName());
	private static Logger logging = null;

	public static void main(String[] args) {
		logger.info("info....");
		logger.fine("fine...");
		logger.warning("warning...");
	}

	@Test
	public void test1() {
		 Logger log = Logger.getLogger("lavasoft");
         log.setLevel(Level.INFO);
         Logger log1 = Logger.getLogger("lavasoft");
         System.out.println(log==log1);     //true
         Logger log2 = Logger.getLogger("lavasoft.blog");
         log2.setLevel(Level.WARNING);

         log.info("aaa");
         log2.info("bbb");
         log2.fine("fine");
         log2.warning("warning");
	}

	@Test
	public void test2() throws SecurityException, IOException {
		Logger log = Logger.getLogger("lavasoft");
        log.setLevel(Level.INFO);
        Logger log1 = Logger.getLogger("lavasoft");
        System.out.println(log==log1);     //true
        Logger log2 = Logger.getLogger("lavasoft.blog");
//        log2.setLevel(Level.WARNING);

        ConsoleHandler consoleHandler =new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        log.addHandler(consoleHandler);
        FileHandler fileHandler = new FileHandler("C:/testlog%g.log");
        fileHandler.setLevel(Level.INFO);
        log.addHandler(fileHandler);
        log.info("aaa");
        log2.info("bbb");
        log2.fine("fine");
	}

	@Test
	public void test3() throws SecurityException, IOException {
		Logger log = Logger.getLogger("lavasoft");
        log.setLevel(Level.INFO);
        Logger log1 = Logger.getLogger("lavasoft");
        System.out.println(log == log1);     //true
        Logger log2 = Logger.getLogger("lavasoft.blog");
//        log2.setLevel(Level.WARNING);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);

        log.addHandler(consoleHandler);
        FileHandler fileHandler = new FileHandler("C:/testlog%g.log");
        fileHandler.setLevel(Level.INFO);
        fileHandler.setFormatter(new MyLogHander());
        log.addHandler(fileHandler);

        log.info("aaa");
        log2.info("bbb");
        log2.fine("fine");

	}

	public Logger getLogger() {
		   if (null == logging) {
	            InputStream is  = LoggerTest.class.getClass().getResourceAsStream("/logging.properties");
	            try {
	                LogManager.getLogManager().readConfiguration(is);
	            } catch (Exception e) {
	                logger.warning("input properties file is error.\n" + e.toString());
	            }finally{
	                try {
	                    is.close();
	                } catch (IOException e) {
	                    logger.warning("close FileInputStream a case.\n" + e.toString());
	                }
	            }

	            logging = Logger.getLogger("LOGGER");
	        }
	        return logging;
	}

	@Test
	public void test4() {
		Logger logging = getLogger();
        logging.finest("finest");
        logging.finer("finer");
        logging.fine("fine");
        logging.info("info");
        logging.config("config");
        logging.warning("warning");
        logging.severe("severe");
	}
}
