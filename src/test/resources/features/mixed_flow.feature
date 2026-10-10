Feature: Mixed API and UI flow

  @api @ui
  Scenario: Execute API setup UI and API verification in the same scenario

    When I send a GET request to the books endpoint and store the response as setup response
    Then the setup API response status code should be 200

    And the ApiDemos application should be displayed

    When I send a GET request to the books endpoint and store the response as verification response
    Then the verification API response status code should be 200