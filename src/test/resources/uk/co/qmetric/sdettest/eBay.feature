Feature: As a new customer
  I want to be able to shop for an item
  and find the cheapest available option
  and use the filtering options

  # There is no definition of "best match"
  # I'm going to assume it means the first result contains the search text
Scenario: A new customer will find a best match by default
  Given I am on the eBay landing page
  When I enter "iPhone" into the search bar
  Then the results display "iPhone" and a price

Scenario: A new customer can search for items ordered by lowest price
  Given I am on the eBay landing page
  When I enter "iPhone" into the search bar
  And I sort by lowest price and PP
  Then the results display "iPhone" and a price
  And are ordered by lowest to highest price

Scenario: A new customer can search for items ordered by highest price
  Given I am on the eBay landing page
  When I enter "iPhone" into the search bar
  And I sort by highest price and PP
  Then the results display "iPhone" and a price
  And are ordered by highest to lowest price

Scenario: A new customer can search for items ordered by highest price
  Given I am on the eBay landing page
  When I enter "iPhone" into the search bar
  And I sort by highest price
  Then the results display "iPhone" and a price
  And are ordered by highest to lowest price

# There is no clear definition of what "newly listed" means.
# I'm going to assume it is an order by date.
Scenario: A new customer can search for newly listed items
  Given I am on the eBay landing page
  When I enter "iPhone" into the search bar
  And I sort by newly listed
  Then the results display "iPhone" and a price
  And are ordered by listing date

Scenario: A new customer can search for auctions
  Given I am on the eBay landing page
  When I enter "iPhone" into the search bar
  And I click the Auction button
  Then the results display "iPhone" and a price
  And are all sold by auction

Scenario: A new customer can search for auctions
  Given I am on the eBay landing page
  When I enter "iPhone" into the search bar
  And I click the Buy It Now button
  Then the results display "iPhone" and a price
  And are all sold as Buy It Now