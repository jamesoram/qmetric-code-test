Feature: As a new customer
  I want to be able to shop for an item
  and find the cheapest available option
  and use the filtering options

Scenario: A new customer will find a best match by default
  Given I am on the eBay landing page
  When I enter "iPhone" into the search bar
  Then the results display "iPhone"

Scenario: A new customer can search for items ordered by lowest price
  Given I am on the eBay landing page
  When I enter "iPhone" into the search bar
  And I sort by lowest price and PP
  Then the results display "iPhone"
  And are ordered by lowest to highest price

Scenario: A new customer can search for items ordered by highest price
  Given I am on the eBay landing page
  When I enter "iPhone" into the search bar
  And I sort by highest price and PP
  Then the results display "iPhone"
  And are ordered by highest to lowest price

Scenario: A new customer can search for items ordered by highest price
  Given I am on the eBay landing page
  When I enter "iPhone" into the search bar
  And I sort by highest price
  Then the results display "iPhone"
  And are ordered by highest to lowest price