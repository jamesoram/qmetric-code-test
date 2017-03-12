package uk.co.qmetric.sdettest;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class EbayStepDefs {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.ebay.co.uk");
    }

    @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
