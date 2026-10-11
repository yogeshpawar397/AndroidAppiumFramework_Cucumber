#Feature: API validation
#
#  @api @api-only
#  Scenario: Get books from BookStore API
#    When I send a GET request to the books endpoint
#    Then the API response status code should be 200
#    And the API response should contain the books
#    And the API response should match the books schema
#
#  @api @api-only
#  Scenario: Add a book without authentication
#    When I send a POST request to add a book
#    Then the API response status code should be 401
#
#  @api @api-only @requiresAuth
#  Scenario: Add a book to the collection
#    When I authenticate with the BookStore API
#    And I send a POST request to add a book
#    Then the API response status code should be 201