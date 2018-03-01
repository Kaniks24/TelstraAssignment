package userStepDefinitions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import Utilities.ascendingCheck;
import Utilities.randomNumber;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinition {

	public WebDriver driver;

	public StepDefinition() {
		driver = Hooks.driver;
	}

	String firstName = "TestFirstName";
	String lastName = "TestLastName";
	String passwd = "password123";
	String company = "TestCompany";
	String addressLine1 = "TestAddress";
	String cityname = "Hartford";
	String zipcode = "01234";
	String mobileNumber = "9876543210";

	ascendingCheck asc = new ascendingCheck();

	// Testing Incorrect Email Register feature
	@Given("^I am on the Sign in page$")
	public void I_am_on_the_Sign_in_page() throws Throwable {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.linkText("Sign in")).click();
	}

	@When("^I enter incorrect email address and clicks on create an account")
	public void I_enter_incorrect_email_address_and_clicks_on_create_an_account() throws Throwable {
		WebElement emailAddress = driver.findElement(By.id("email_create"));
		WebElement createAnAccount = driver.findElement(By.id("SubmitCreate"));

		emailAddress.sendKeys("Test123gmail.com");
		createAnAccount.submit();
	}

	@Then("^I should see an error message$")
	public void I_should_see_an_error_message() throws Throwable {
		String message = driver.findElement(By.xpath("//form/div/div[1]/ol/li")).getText();
		Assert.assertEquals("Invalid email address.", message);

	}

	// Testing valid Email Register feature

	@When("^I fill in valid Email address and click on create an account$")
	public void I_fill_in_valid_Email_address_and_click_on_create_an_account() throws Throwable {

		randomNumber r = new randomNumber();
		String emailString = "Example" + r.gen();
		String emailAddress = emailString + "@gmail.com";
		driver.findElement(By.id("email_create")).sendKeys(emailAddress);
		driver.findElement(By.id("SubmitCreate")).submit();
	}

	@And("^I fill in personal information on create Account page and click on Register$")
	public void I_fill_in_personal_information_on_create_Account_page_and_click_on_Register() throws Throwable {

		WebElement Title = driver.findElement(By.id("id_gender2"));
		WebElement customer_firstname = driver.findElement(By.id("customer_firstname"));
		WebElement customer_lastname = driver.findElement(By.id("customer_lastname"));
		WebElement password = driver.findElement(By.id("passwd"));
		WebElement address_company = driver.findElement(By.id("company"));
		WebElement address = driver.findElement(By.id("address1"));
		WebElement city = driver.findElement(By.id("city"));
		WebElement postalcode = driver.findElement(By.id("postcode"));
		WebElement MobilePhone = driver.findElement(By.id("phone_mobile"));
		WebElement Register = driver.findElement(By.id("submitAccount"));

		Title.click();
		customer_firstname.sendKeys(firstName);
		customer_lastname.sendKeys(lastName);
		password.sendKeys(passwd);
		address_company.sendKeys(company);
		address.sendKeys(addressLine1);
		city.sendKeys(cityname);
		Select drpState = new Select(driver.findElement(By.name("id_state")));
		drpState.selectByVisibleText("Connecticut");
		postalcode.sendKeys(zipcode);
		Select drpCountry = new Select(driver.findElement(By.name("id_country")));
		drpCountry.selectByVisibleText("United States");
		MobilePhone.sendKeys(mobileNumber);
		Register.click();

	}

	@Then("^I should see My Account page$")
	public void user_should_see_My_Account_page() throws Throwable {
		String confmessage = driver.findElement(By.linkText("My account")).getText();
		Assert.assertEquals("My account", confmessage);
	}

	// MegaMenu Feature

	@When("^I hover on Dresses on MegaMenu$")
	public void I_hover_on_Dresses_on_MegaMenu() throws InterruptedException {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement megaMenu = driver
				.findElement(By.xpath("/html/body/div[1]/div[1]/header/div[3]/div/div/div[6]/ul/li[2]/a"));
		Actions action = new Actions(driver);
		action.moveToElement(megaMenu).build().perform();
		Thread.sleep(3000);
	}

	@And("^I click on Summer Dresses on sub menu$")
	public void I_click_on_Summer_Dresses_on_sub_menu() throws Throwable {

		WebElement subMenu = driver
				.findElement(By.xpath("/html/body/div[1]/div[1]/header/div[3]/div/div/div[6]/ul/li[2]/ul/li[3]/a"));
		subMenu.click();
	}

	@Then("^I should see Summer Dresses page$")
	public void I_should_see_Summer_Dresses_page() throws Throwable {

		String confmessage = driver.findElement(By.xpath("//span[@class='cat-name']")).getText();
		System.out.println(confmessage);
		Assert.assertEquals("SUMMER DRESSES ", confmessage);
	}

	// Product Grid feature

	@Given("^I am on Summer Dresses page$")
	public void I_am_on_Summer_Dresses_page() throws Throwable {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://automationpractice.com/index.php?id_category=11&controller=category#/");

	}

	@When("^I click on Sort By Price$")
	public void I_click_on_Sort_By_Price() throws InterruptedException {

		Select sortBy = new Select(driver.findElement(By.id("selectProductSort")));
		sortBy.selectByVisibleText("Price: Lowest first");
		Thread.sleep(3000);
		List<WebElement> price = driver.findElements(By.xpath("//span[@class='price product-price']"));

		List<String> prices = new ArrayList<String>();
		for (WebElement e : price) {
			prices.add(e.getText());
		}
		prices.removeAll(Arrays.asList(null, ""));

		List<String> sortedPrices = new ArrayList<String>(prices);
		sortedPrices.removeAll(Arrays.asList(null, ""));
		// sort the list
		Collections.sort(sortedPrices);
		System.out.println(prices);
		System.out.println(sortedPrices);
		asc.setFlag(sortedPrices.equals(prices));
	}

	@Then("^I should see the product grid is arranged properly$")
	public void I_should_see_the_product_grid_is_arranged_properly() throws Throwable {
		boolean flag = asc.isFlag();
		System.out.println(flag);
		Assert.assertEquals(true, flag);

	}
}
