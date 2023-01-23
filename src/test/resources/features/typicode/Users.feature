Feature: users endpoint
  As a user
  I should be able to access users endpoint
  So that I can successfully perform actions on it

  @get
  Scenario: Get Users
    Given The endpoint for users exist
    When I call the endpoint to retrieve users
    Then The endpoint should return status of 200
    And I should see the list of users returned by the endpoint

  @post
  Scenario: Create User
    Given The endpoint for users exist
    When I call the endpoint to create users
    Then The endpoint should return status of 201
    And The user should be successfully created

  @put
  Scenario: Update User
    Given The endpoint for users exist
    And For an existing user
    When I update the user
    Then The endpoint should return status of 200
    And the body should have updated user information

  @patch
  Scenario Outline: Partial Update To User
    Given The endpoint for users exist
    And For an existing user
    When I partially update the user with "<fieldToChane>" "<value>"
    Then The endpoint should return status of 200
    And the body should have updated user information
    Examples:
      | fieldToChane | value        |
      | username         | new_name |

  @delete
  Scenario: Delete User
    Given The endpoint for users exist
    When I delete an existing user
    Then The endpoint should return status of 200
    And I should see an empty body returned by the endpoint


  @noheader @negative
  Scenario: Create User with no header
    Given The endpoint for users exist
    When I call the endpoint to create user with no header
    Then The endpoint should return status of 201
    And The user should be successfully created with no body but Id

