package uk.co.qmetric.sdettest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EbaySearchResultsPage extends EbayPage {

    @FindBy(xpath = "id('DashSortByContainer')/ul[1]/li/a")
    private WebElement sortMenuList;

    @FindBy(xpath = "id('SortMenu')/li[1]/a")
    private WebElement lowestPricePPLink;

    @FindBy(xpath = "id('ListViewInner')/li[1]/h3")
    private WebElement firstSearchResult;

    private static final String SEARCH_RESULT_BY_INDEX = "id('ListViewInner')/li[%s]/h3";
    private static final String RESULT_PRICE_BY_INDEX =
            "//li[contains(@class, 'sresult')][%s]//li[contains(@class, 'prc')]/span";

    public EbaySearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public EbaySearchResultsPage clickSortMenu() {
        sortMenuList.click();
        return this;
    }

    public EbaySearchResultsPage sortByLowestPriceAndPp() {
        clickSortMenu();
        lowestPricePPLink.click();
        return this;
    }

    public String getFirstResultTitle() {
        try {
            return firstSearchResult.getText();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            return "";
        }
    }

    public String getNthResultTitle(int n) {
        return getTextByIndex(firstSearchResult, n, SEARCH_RESULT_BY_INDEX);
    }

    public String getNthResultPrice(int n) {
        return getTextByIndex(firstSearchResult, n, RESULT_PRICE_BY_INDEX);
    }
}
