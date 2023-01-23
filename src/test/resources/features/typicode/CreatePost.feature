Feature: Create Post
  As a user
  I should be able to create post
  So that I can successfully post

  Scenario: Retrieve Posts
    Given The endpoint for post exist
    When I call the endpoint to retrieve posts
    Then The endpoint should return status of 200
    And I should see the list of posts returned by the endpoint

    @postive
  Scenario Outline: Create Post
    Given The endpoint for post exist
    When I call the endpoint to create post with "<userId>" "<title>" "<body>"
    Then The endpoint should return status of 201
    And The post should be successfully created with "<userId>" "<title>" "<body>"
      Examples:
        | userId | title                 | body        |
        | 1      | Sundays are relaxing! | Relaxing    |
        | 2A     | Cold Week             | Freezing... |
        |        | Cold Week             | Freezing... |
        |        |                       |             |

   @noheader
  Scenario: Create Post
    Given The endpoint for post exist
    When I call the endpoint to create post with "01" "No Header" "Headerless>"
    Then The endpoint should return status of 201
    And The post should be successfully created with no body

  @put
  Scenario Outline: Update Posts
    Given The endpoint for post exist
    And For an existing post
    When I update the post with "<userId>" "<title>" "<body>"
    Then The endpoint should return status of 200
    And the body should have updated post information
    Examples:
      | userId | title     | body        |
      | 1      | test 01  | test@001.com |

  @patch
  Scenario Outline: Partial Update To Posts
    Given The endpoint for post exist
    And For an existing post
    When I partially update the post with "<fieldToChane>" "<value>"
    Then The endpoint should return status of 200
    And the body should have updated post information
    Examples:
      | fieldToChane | value          |
      | name         | test 02        |

  @delete
  Scenario: Delete Comments
    Given The endpoint for post exist
    When I delete an existing post
    Then The endpoint should return status of 200
    And I should see an empty body returned by the endpoint