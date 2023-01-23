package com.typicode.app.typicode.stepdefs;

import com.typicode.app.typicode.base.TestBase;
import com.typicode.app.typicode.msgType.Comments;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import java.util.Arrays;
import java.util.List;

import static com.typicode.app.typicode.helper.TypicodeHelper.*;
import static com.typicode.app.typicode.helper.TypicodeHelper.getJsonNodeFromType;
import static com.typicode.app.typicode.utils.Constants.BASE_URL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Vinod Kris
 */
public class CommentsSteps {

    private final TestBase testBase;
    private static final String COMMENTS_URL = BASE_URL + "comments";
    private Comments comment;
    private Comments existingComment;

    public CommentsSteps(TestBase testBase) {
        this.testBase = testBase;
    }

    @Given("The endpoint for comments exist")
    public void the_endpoint_for_comments_exist() {
        RestAssured.given()
                .baseUri(COMMENTS_URL)
                .contentType("application/json");
    }
    @When("I call the endpoint to retrieve comments")
    public void i_call_the_endpoint_to_retrieve_comments() {
        testBase.response = RestAssured.when().
                get(COMMENTS_URL);
    }

    @Then("I should see the list of comments returned by the endpoint")
    public void i_should_see_the_list_of_comments_returned_by_the_endpoint() {
        List<Comments> comments = Arrays.asList(testBase.response.getBody().as(Comments[].class));
        assertTrue("No Comments returned",!comments.isEmpty());
    }

    @When("I call the endpoint to post comments with {int} {string} {string} {string}")
    public void i_call_the_endpoint_to_post_comments_with(int postId, String name, String email, String body) {
        Comments comment = new Comments(postId,name,email,body);
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(comment)
                .post(COMMENTS_URL);
    }
    @Then("The comment should be successfully posted with {int} {string} {string} {string}")
    public void the_comment_should_be_successfully_posted_with(int postId, String name, String email, String body) {
        Comments comments = testBase.response.getBody().as(Comments.class);
        Comments expectedComment = new Comments(postId,name,email,body);
        System.out.println("SETTING ID OF EXPECTED COMMENT TO MATCH THE ACTUAL ONE");
        expectedComment.setId(comments.getId());
        assertEquals(getJsonNodeFromType(expectedComment), getJsonNodeFromType(comments));
    }

    @Given("For an existing comment")
    public void for_an_existing_comment() {
        List<Comments> comments = Arrays.asList(RestAssured
                .when()
                .get(COMMENTS_URL)
                .getBody().as(Comments[].class));
        existingComment = comments.get(0);
    }

    @When("I update the comment with {int} {string} {string} {string}")
    public void i_update_the_comment_with(int postId, String name, String email, String body) {
        comment = new Comments(postId,name,email,body);
        comment.setId(existingComment.getId());
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(comment)
                .put(COMMENTS_URL + "/" + comment.getId());
    }

    @When("I partially update the comment with {string} {string}")
    public void i_partially_update_the_comment_with(String fieldToChange, String value) {

        String jsonBody = buildJsonStringWithGivenField(fieldToChange,value);

        comment = updateComment(existingComment,fieldToChange,value);
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .patch(COMMENTS_URL + "/" + existingComment.getId());
        System.out.println(testBase.response.getBody().asPrettyString());
    }

    @Then("the body should have updated information")
    public void the_body_should_have_updated_information() {

        Comments updatedComment = testBase.response.getBody().as(Comments.class);
        assertEquals(getJsonNodeFromType(comment), getJsonNodeFromType(updatedComment));

    }

    @When("I delete an existing comment")
    public void i_delete_an_existing_comment() {
        List<Comments> comments = Arrays.asList(RestAssured
                .when()
                .get(COMMENTS_URL)
                .getBody().as(Comments[].class));
        existingComment = comments.get(0);
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .delete(COMMENTS_URL + "/" + existingComment.getId());
    }

    @When("I call the endpoint to create comment with no header")
    public void i_call_the_endpoint_to_create_comment_with_no_header() {
        Comments commentWithNoHeader = new Comments(1,"test","test@a.com","Negative test with no header");
        testBase.response = RestAssured.given()
                .body(commentWithNoHeader)
                .post(COMMENTS_URL);
    }

    @When("I call the endpoint to create comment with no body")
    public void i_call_the_endpoint_to_create_comment_with_no_body() {
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .post(COMMENTS_URL);
    }

    @Then("The comment should be successfully created with no body but Id")
    public void the_comment_should_be_successfully_created_with_no_body_but_id() {

        Comments withNoBody = testBase.response.body()
                .as(Comments.class);
        assertTrue("",withNoBody.getBody() == null);
        assertTrue("",withNoBody.getPostId() == 0);
        assertTrue("",withNoBody.getEmail() == null);
        assertTrue("",withNoBody.getName() == null);
        assertTrue("",withNoBody.getId() != 0);

    }

}
