Feature: General Store form validation

#  @Form
#  Scenario: Error validation when customer name is blank
#    Given I launch the General Store form
#    When I select female gender
#    And I select the country "Argentina"
#    And I submit the form without entering a name
#    Then the error toast "Please enter your name" should be displayed

  @Form
  Scenario: Positive flow when customer name is entered
    Given I launch the General Store form
    When I enter the name "Alex Smith"
    And I select female gender
    And I select the country "Argentina"
    And I submit the form
    Then the form should submit without an error toast
