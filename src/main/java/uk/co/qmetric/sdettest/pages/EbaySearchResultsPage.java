package uk.co.qmetric.sdettest.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EbaySearchResultsPage extends EbayPage {

    @FindBy(xpath = "id('DashSortByContainer')/ul[1]/li/a")
    private WebElement sortMenuList;

    @FindBy(xpath = "id('SortMenu')/li[1]/a")
    private WebElement lowestPricePPLink;

    @FindBy(xpath = "id('SortMenu')/li[2]/a")
    private WebElement highestPricePPLink;

    @FindBy(xpath = "id('SortMenu')/li[5]/a")
    private WebElement newlyListedLink;

    @FindBy(xpath = "id('SortMenu')/li[3]/a")
    private WebElement highestPriceLink;

    @FindBy(xpath = "id('ListViewInner')/li[1]/h3")
    private WebElement firstSearchResult;

    @FindBy(xpath = "id('cbelm')/div[1]/div[2]/a[1]")
    private WebElement auctionButton;

    @FindBy(xpath = "id('cbelm')/div[1]/div[2]/a[2]")
    private WebElement buyItNowButton;

    @FindBy(xpath = "//li[contains(@class, 'sresult')][1]//li[contains(@class, 'lvshipping')]/span/span")
    private WebElement firstResultShippingPrice;

    @FindBy(xpath = "//li[contains(@class, 'sresult')][1]//li[contains(@class, 'lvformat')]/span")
    private WebElement firstResultBids;

    private static final String LISTING_TIME_FORMAT = "dd-MMM HH:mm";
    private static final String SEARCH_RESULT_BY_INDEX = "id('ListViewInner')/li[%s]/h3";
    private static final String RESULT_PRICE_BY_INDEX =
            "//li[contains(@class, 'sresult')][%s]//li[contains(@class, 'prc')]/span";
    private static final String RESULT_LISTING_DATE_BY_INDEX =
            "id('ListViewInner')/li[%s]//span[@class=\"tme\"]/span";
    private static final String RESULT_TYPE_BY_INDEX =
            "//li[contains(@class, 'sresult')][%s]//li[contains(@class, 'lvformat')]/span";

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
        return new EbaySearchResultsPage(driver);
    }

    public EbaySearchResultsPage sortByHighestPriceAndPp() {
        clickSortMenu();
        highestPricePPLink.click();
        return new EbaySearchResultsPage(driver);
    }

    public EbaySearchResultsPage sortByHighestPrice() {
        clickSortMenu();
        highestPriceLink.click();
        return new EbaySearchResultsPage(driver);
    }

    public EbaySearchResultsPage sortByNewlyListed() {
        clickSortMenu();
        newlyListedLink.click();
        return new EbaySearchResultsPage(driver);
    }

    public EbaySearchResultsPage clickAuctionButton() {
        auctionButton.click();
        return new EbaySearchResultsPage(driver);
    }

    public EbaySearchResultsPage clickBuyItNowButton() {
        buyItNowButton.click();
        return new EbaySearchResultsPage(driver);
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

    public Date getResultListingDateByIndex(int n) {
        try {
            String listingDateString = getTextByIndex(firstSearchResult, n, RESULT_LISTING_DATE_BY_INDEX);
            Date listingDate = new SimpleDateFormat(LISTING_TIME_FORMAT).parse(listingDateString);
            return listingDate;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            throw new RuntimeException("Invalid listing date");
        }
    }

    public boolean isResultAnAuction(int n) {
        return getTextByIndex(firstSearchResult, n, RESULT_TYPE_BY_INDEX).contains("bid");
    }

    public boolean firstResultHasFreePp() {
        return firstResultShippingPrice.getText().contains("Free");
    }

    public int getBidsOnFirstItem() {
        try {
            return Integer.parseInt(firstResultBids.getText().replace(" bids", ""));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            throw new RuntimeException("Could not get number of bids for first search result");
        }
    }
}
