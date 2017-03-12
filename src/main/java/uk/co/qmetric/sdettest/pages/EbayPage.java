package uk.co.qmetric.sdettest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EbayPage extends AbstractPage {

    @FindBy(id = "gh-ac")
    private WebElement searchBar;

    @FindBy(id = "gh-btn")
    private WebElement searchButton;

    public EbayPage(WebDriver driver) {
        super(driver);
    }

    public EbayPage enterSearchQuery(String query) {
        searchBar.sendKeys(query);
        return this;
    }

    public EbaySearchResultsPage clickSearchButton() {
        searchButton.click();
        return new EbaySearchResultsPage(driver);
    }

    public EbaySearchResultsPage search(String query) {
        return enterSearchQuery(query).clickSearchButton();
    }
}
