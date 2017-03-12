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

import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.junit.Assert.assertThat;
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

    @Then("the results display \"(.+)\"")
    public void thenResultsDisplay(String expectedResult) {
        // verify top 3 results
        for (int i = 1; i <= 3; i++) {
            String actualResult = ((EbaySearchResultsPage)currentPage).getNthResultTitle(i);

            assertThat("Result number " + i + " does not contain " + expectedResult,
                    actualResult, containsIgnoringCase(expectedResult));
        }
    }

    @Then("are ordered by price")
    public void areOrderedByPrice() {
        float firstPrice = Float.parseFloat(((EbaySearchResultsPage)currentPage).getNthResultPrice(1).replace("£", ""));
        float secondPrice = Float.parseFloat(((EbaySearchResultsPage)currentPage).getNthResultPrice(2).replace("£", ""));
        float thirdPrice = Float.parseFloat(((EbaySearchResultsPage)currentPage).getNthResultPrice(3).replace("£", ""));

        assertThat("Prices are not in ascending order", firstPrice, lessThan(secondPrice));
        assertThat("Prices are not in ascending order", secondPrice, lessThan(thirdPrice));
    }
}
