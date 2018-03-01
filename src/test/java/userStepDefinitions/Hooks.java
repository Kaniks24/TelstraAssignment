package userStepDefinitions;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
	
	public static WebDriver driver;

	@Before
	public void openBrowser() throws MalformedURLException {
		//String browser = System.getProperty("BROWSER");
		String browser = getParameter("BROWSER");
		if (browser == "chrome") {
			System.setProperty("webdriver.chrome.driver","/Users/Shubham_MacbookPro/Desktop/chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser == "firefox") {
			driver = new FirefoxDriver();
		} else {
			driver = new FirefoxDriver();
		}
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String weburl = "http://automationpractice.com";
		driver.get(weburl);
	}

	private String getParameter(String string) {
		return null;
	}

	@After
	public void embedScreenshot(Scenario scenario) {
		if (scenario.isFailed()) {
			try {
				scenario.write("Current Page URL is " + driver.getCurrentUrl());
				byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenshot, "image/png");
			} catch (WebDriverException somePlatformsDontSupportScreenshots) {
				System.err.println(somePlatformsDontSupportScreenshots.getMessage());
			}
		}
		driver.quit();
	}


}
