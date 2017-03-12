package uk.co.qmetric.sdettest;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import uk.co.qmetric.sdettest.pages.EbayPage;
import uk.co.qmetric.sdettest.pages.EbaySearchResultsPage;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.OrderingComparison.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static uk.co.qmetric.sdettest.matchers.CaseInsensitiveSubstringMatcher.containsIgnoringCase;

public class EbayStepDefs {

    private static final String HOME_PAGE = "http://www.ebay.co.uk";
    private WebDriver driver;
    private EbayPage currentPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }

    @Given("I am on the eBay landing page")
    public void givenIAmOnTheEbayLandingPage() {
        driver.get(HOME_PAGE);
        currentPage = new EbayPage(driver);
    }

    @When("I enter \"(.+)\" into the search bar")
    public void whenIEnterIntoTheSearchBar(String query) {
        currentPage = currentPage.search(query);
    }

    @When("I sort by lowest price and P+P")
    public void whenIFilterByLowestPriceAndPp() {
        currentPage = ((EbaySearchResultsPage)currentPage).sortByLowestPriceAndPp();
    }

    @When("^I sort by highest price and PP$")
    public void whenIFilterByHighestPriceAndPp() {
        currentPage = ((EbaySearchResultsPage)currentPage).sortByHighestPriceAndPp();
    }

    @When("^I sort by highest price$")
    public void whenIFilterByHighestPrice() {
        currentPage = ((EbaySearchResultsPage)currentPage).sortByHighestPrice();
    }

    @When("I sort by newly listed")
    public void whenISortByNewlyListed() {
        currentPage = ((EbaySearchResultsPage)currentPage).sortByNewlyListed();
    }

    @When("I click the Auction button")
    public void whenIClickTheAuctionButton() {
        currentPage = ((EbaySearchResultsPage)currentPage).clickAuctionButton();
    }

    @When("I click the Buy It Now button")
    public void whenIClickTheBuyItNowButton() {
        currentPage = ((EbaySearchResultsPage)currentPage).clickBuyItNowButton();
    }

    @Then("the results display \"(.+)\" and a price")
    public void thenResultsDisplay(String expectedResult) {
        // verify top 3 results
        for (int i = 1; i <= 3; i++) {
            String actualResult = ((EbaySearchResultsPage)currentPage).getNthResultTitle(i);

            assertThat("Result number " + i + " does not contain " + expectedResult,
                    actualResult, containsIgnoringCase(expectedResult));
            assertThat("Result doesn't have a price", getPrice(i), greaterThanOrEqualTo(0f));
        }
    }

    @Then("are ordered by lowest to highest price")
    public void areOrderedByLowestPrice() {
        float firstPrice = getPrice(1);
        float secondPrice = getPrice(2);
        float thirdPrice = getPrice(3);

        assertThat("Prices are not in ascending order", firstPrice, lessThanOrEqualTo(secondPrice));
        assertThat("Prices are not in ascending order", secondPrice, lessThanOrEqualTo(thirdPrice));

        // We are going to hope that the first result has free P&P
        // however, this is not good unless we can control the test data
        boolean hasFreePp = ((EbaySearchResultsPage)currentPage).firstResultHasFreePp();
        assertThat("First item doesn't have free P&P", hasFreePp, is(true));
    }

    @Then("are ordered by highest to lowest price")
    public void areOrderedByHighestPrice() {
        float firstPrice = getPrice(1);
        float secondPrice = getPrice(2);
        float thirdPrice = getPrice(3);

        assertThat("Prices are not in descending order", firstPrice, greaterThanOrEqualTo(secondPrice));
        assertThat("Prices are not in descending order", secondPrice, greaterThanOrEqualTo(thirdPrice));
    }

    @Then("are ordered by listing date")
    public void thenResultsAreOrderedByListingDate() {
        Date firstDate = ((EbaySearchResultsPage)currentPage).getResultListingDateByIndex(1);
        Date secondDate = ((EbaySearchResultsPage)currentPage).getResultListingDateByIndex(2);
        Date thirdDate = ((EbaySearchResultsPage)currentPage).getResultListingDateByIndex(3);

        assertThat("Items are not ordered by date", firstDate, greaterThanOrEqualTo(secondDate));
        assertThat("Items are not ordered by date", secondDate, greaterThanOrEqualTo(thirdDate));
    }

    @Then("are all sold by auction")
    public void thenAreAllSoldByAuction() {
        // we consider the top 10 to be 'all'
        verifyTopTenAreAuction(true);
        int bidsOnFirstItem = ((EbaySearchResultsPage)currentPage).getBidsOnFirstItem();
        assertThat("First item doesn't have bids", bidsOnFirstItem, greaterThanOrEqualTo(0));
    }

    @Then("are all sold as Buy It Now")
    public void thenAllAreSoldAsBuyItNow() {
        // What isn't an auction is "buy it now"
        verifyTopTenAreAuction(false);
    }

    private void verifyTopTenAreAuction(boolean expectedResult) {
        for (int i = 1; i <= 10; i++) {
            boolean isAuction = ((EbaySearchResultsPage)currentPage).isResultAnAuction(i);
            assertThat("Result " + i + " was not an auction", isAuction, is(expectedResult));
        }
    }

    private float getPrice(int resultIndex) {
        try {
            String price = ((EbaySearchResultsPage) currentPage)
                    .getNthResultPrice(resultIndex)
                    .replace("£", "")
                    .replace(",", "")
                    .split(" ")[0];
            return Float.parseFloat(price);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            fail("Invalid price found on result number " + resultIndex);
            return  0f;
        }
    }
}
