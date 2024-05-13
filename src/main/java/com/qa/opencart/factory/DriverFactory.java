package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exception.FrameworkException;

public class DriverFactory {

	public WebDriver driver;
	public Properties prop;
	public OptionsManager optionsManager;

	public static String highlight;

    
	
    //private final Logger logger = Logger.getLogger(DriverFactory.class);

    

	/**
	 * this method is initializing the driver on the basis of given browser name
	 * 
	 * @param browserName
	 * @return this returns the driver
	 */
	
	public WebDriver initDriver(Properties prop) {
		
		String browserName = prop.getProperty("browser").toLowerCase().trim();
		System.out.println("browser name is : " + browserName);
		optionsManager = new OptionsManager(prop);
		if(browserName.equalsIgnoreCase("chrome"))
				{
			System.setProperty("webdriver.chrome.driver","D:\\Chrome_driver\\chromedriver-win64\\chromedriver.exe");
			driver =new ChromeDriver();
			       
				}
		else if(browserName.equalsIgnoreCase("firefox"))
				{
			 driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
				}
		else if(browserName.equalsIgnoreCase("safari"))
				{
			 driver = new SafariDriver();
				}
		else if(browserName.equalsIgnoreCase("edge"))
				{
			 driver = new EdgeDriver(optionsManager.getEdgeOptions());
				}
		else
		{
			System.out.println("Please pass the right browser name"+ browserName);
			throw new FrameworkException("NO BROWSER FOUND EXCEPTION....");
		}
		

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(prop.getProperty("url").trim());
		return driver;
	}
	
	
	
	
	
	
	
	/*
	 * get the local thread copy of the driver
	 */
	

	/**
	 * this method is reading the properties from the .properties file
	 * 
	 * @return
	 */
	public Properties initProp() {

		// mvn clean install -Denv="qa"
		// mvn clean install
		prop = new Properties();
		FileInputStream ip = null;
		String envName = System.getProperty("env");
		System.out.println("Running test cases on Env: " + envName);

		try {
			if (envName == null) {
				System.out.println("no env is passed....Running tests on QA env...");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					System.out.println("....Wrong env is passed....No need to run the test cases....");
					throw new FrameworkException("WRONG ENV IS PASSED...");
				// break;
				}

			}
		} catch (FileNotFoundException e) {

		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}
	/**
	 * take screenshot
	 */
	public  String getScreenshot() {
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileHandler.copy(file, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

}