Feature: /comments endpoint
  As a user
  I should be able to access /comments endpoint
  So that I can successfully perform actions on it

  Scenario: Retrieve Comments
    Given The endpoint for comments exist
    When I call the endpoint to retrieve comments
    Then The endpoint should return status of 200
    And I should see the list of comments returned by the endpoint

  @positive
  Scenario Outline: Post Comments
    Given The endpoint for comments exist
    When I call the endpoint to post comments with "<postId>" "<name>" "<email>" "<body>"
    Then The endpoint should return status of 201
    And The comment should be successfully posted with "<postId>" "<name>" "<email>" "<body>"
    Examples:
      | postId | name     | email        | body         |
      | 1      | test 01  | test@001.com |  test body   |
      | 2A     | test 02  | test@002.com | test body    |
      |        |          | test@001.com | test body    |

    @put
  Scenario Outline: Update Comments
    Given The endpoint for comments exist
    And For an existing comment
    When I update the comment with "<postId>" "<name>" "<email>" "<body>"
    Then The endpoint should return status of 200
    And the body should have updated information
    Examples:
      | postId | name     | email        | body         |
      | 1      | test 01  | test@001.com |  test body   |

      @patch
  Scenario Outline: Partial Update To Comments
    Given The endpoint for comments exist
    And For an existing comment
    When I partially update the comment with "<fieldToChane>" "<value>"
    Then The endpoint should return status of 200
    And the body should have updated information
    Examples:
      | fieldToChane | value          |
      | name         | test 02        |

        @delete
  Scenario: Delete Comments
    Given The endpoint for comments exist
    When I delete an existing comment
    Then The endpoint should return status of 200
    And I should see an empty body returned by the endpoint