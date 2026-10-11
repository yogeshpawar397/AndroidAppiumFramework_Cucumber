#Feature: Shopping cart total
#
#  @ui @CartPage
#  Scenario: Verify total purchase amount for the first two listed products
#    Given I launch the General Store form
#    When I enter the name "Alex Smith"
#    And I select female gender
#    And I select the country "Argentina"
#    And I submit the form
#    And I add the first 2 products to the cart
#    And I open the shopping cart
#    Then the total purchase amount should equal the sum of the product prices
