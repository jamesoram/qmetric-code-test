* Simple tests for eBay search using Cucumber-JVM and WebDriver.

In order to run them you will need Java, Google Chrome, Chromedriver and Maven.

Once you have them all installed you can run:

```mvn clean test```

* Simple jmeter test for eBay search.

In order to run it you will need jmeter.

Once you have it installed it can be run from the command line with:

```jmeter -n -t ebay.jmx```

The reporting is better when using the GUI. In order to do this run:

```jmeter -t ebay.jmx```

And press play. Once complete you can see the results by clicking on "Simple search" and then on "Graph results for eBay search", "Summary report" and "View results in table".

The average search takes 2115 ms on my computer.
