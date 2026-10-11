#Feature: Shopping cart total and web browser checkout
#
#  @ui @WebBrowser
#  Scenario: Verify cart total and continue checkout in the web browser
#    Given I launch the General Store form
#    When I enter the name "Alex Smith"
#    And I select female gender
#    And I select the country "Argentina"
#    And I submit the form
#    And I add the first 2 products to the cart
#    And I open the shopping cart
#    Then the total purchase amount should equal the sum of the product prices
#    When I complete the shopping cart order
#    And I switch to the web browser page
#    And I search the web browser for "Postnod Website"
#    And I return to the native app
