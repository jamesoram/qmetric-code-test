package uk.co.qmetric.sdettest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public abstract class AbstractPage {

    protected WebDriver driver;
    private WebDriverWait wait;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        this.wait = new WebDriverWait(driver, 30);
    }

    public void waitForElementPresent(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected String getTextByIndex(WebElement triggerElement, int n, String xpath) {
        waitForElementPresent(triggerElement);
        try {
            return driver.findElement(By.xpath(String.format(xpath, n))).getText();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            return "";
        }
    }
}
