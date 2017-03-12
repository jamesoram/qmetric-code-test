package uk.co.qmetric.sdettest;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"html:target/cucumber-html-report"},
        features = {"src/test/resources/uk/co/qmetric/sdettest/eBay.feature"}
)
public class RunCukesTest {

}
