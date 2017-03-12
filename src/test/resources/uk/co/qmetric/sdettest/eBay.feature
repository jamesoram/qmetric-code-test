Feature: As a new customer
  I want to be able to shop for an item
  and find the cheapest available option
  and use the filtering options

Scenario: A new customer can search for items ordered by price
  Given I am on the eBay landing page
  When I enter "iPhone" into the search bar
  And I sort by lowest price and PP
  Then the results display "iPhone"
  And are ordered by price