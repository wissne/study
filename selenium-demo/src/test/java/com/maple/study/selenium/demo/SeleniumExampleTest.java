package com.maple.study.selenium.demo;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SeleniumExampleTest {

	@Test
	public void test() {
		
		DesiredCapabilities capability=DesiredCapabilities.internetExplorer(); 
		 capability.setCapability( 
		              InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); 
		 System.setProperty("webdriver.ie.driver", 
				 "src/main/resources/IEDriverServer.exe"); 
		 
		 WebDriver webdriver = new InternetExplorerDriver(capability);

		webdriver.get("www.baidu.com");
		webdriver.quit();
	}
}
