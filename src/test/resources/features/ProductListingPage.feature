#Feature: General Store product listing
#
#  @ui @ProductListingPage
#  Scenario: Add a selected product from the catalogue to the shopping cart
#    Given I launch the General Store form
#    When I enter the name "Alex Smith"
#    And I select female gender
#    And I select the country "Argentina"
#    And I submit the form
#    And I scroll down until the product "Jordan 6 Rings" is visible
#    And I add the product "Jordan 6 Rings" to the cart
#    And I open the shopping cart
#    Then the shopping cart should contain the product "Jordan 6 Rings"
