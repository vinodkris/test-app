package com.typicode.app.typicode.stepdefs;

import com.typicode.app.typicode.base.TestBase;
import com.typicode.app.typicode.msgType.Post;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import java.util.Arrays;
import java.util.List;
import static com.typicode.app.typicode.helper.TypicodeHelper.*;
import static com.typicode.app.typicode.utils.Constants.POST_URL;
import static org.junit.Assert.*;

/*
    Stepdef class specific to posts endpoint
 */

public class CreatePostSteps {

    private final TestBase testBase;
    private Post existingPost;
    private Post post;

    public CreatePostSteps(TestBase testBase) {
        this.testBase = testBase;
    }

    @Given("The endpoint for post exist")
    public void the_endpoint_for_post_exist() {

        RestAssured.given()
                .baseUri(POST_URL)
                .contentType("application/json");
    }

    @When("I call the endpoint to retrieve posts")
    public void i_call_the_endpoint_to_retrieve_posts() {

        testBase.response = RestAssured.when().
                get(POST_URL);
    }

    @Then("I should see the list of posts returned by the endpoint")
    public void i_should_see_the_list_of_posts_returned_by_the_endpoint() {

        List<Post> posts = Arrays.asList(testBase.response.getBody().as(Post[].class));
        assertTrue("No Posts returned",!posts.isEmpty());
    }

    @When("I call the endpoint to create post with {int} {string} {string}")
    public void i_call_the_endpoint_to_create_post_with(int userId, String title, String body) {

        Post post = buildPost(userId,title,body);
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(post)
                .post(POST_URL);
    }

    @Then("The post should be successfully created with {int} {string} {string}")
    public void the_post_should_be_successfully_created_with(int userId, String title, String body) {

        Post expectedPost = new Post(title,body,userId);
        Post post = testBase.response.getBody().as(Post.class);
        expectedPost.setId(post.getId());
        assertEquals(getJsonNodeFromType(expectedPost), getJsonNodeFromType(post));
    }

    @Given("For an existing post")
    public void for_an_existing_post() {

        List<Post> posts = Arrays.asList(RestAssured
                .when()
                .get(POST_URL)
                .getBody().as(Post[].class));
        existingPost = posts.get(0);
    }

    @When("I update the post with {int} {string} {string}")
    public void i_update_the_post_with(int userId, String title, String body) {

        post = new Post(title,body,userId);
        post.setId(existingPost.getId());
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(post)
                .put(POST_URL + "/" + post.getId());
    }

    @When("I partially update the post with {string} {string}")
    public void i_partially_update_the_post_with(String fieldToChange, String value) {

        String jsonBody = buildJsonStringWithGivenField(fieldToChange,value);

        post = updatePost(existingPost,fieldToChange,value);
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .patch(POST_URL + "/" + existingPost.getId());
    }

    @Then("the body should have updated post information")
    public void the_body_should_have_updated_post_information() {

        Post updatedPost = testBase.response.getBody().as(Post.class);
        post.setId(updatedPost.getId());
        assertEquals(getJsonNodeFromType(post), getJsonNodeFromType(updatedPost));
    }

    @When("I delete an existing post")
    public void i_delete_an_existing_post() {

        List<Post> comments = Arrays.asList(RestAssured
                .when()
                .get(POST_URL)
                .getBody().as(Post[].class));
        existingPost = comments.get(0);
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .delete(POST_URL + "/" + existingPost.getId());
    }

    @When("I call the endpoint to create post with no header")
    public void i_call_the_endpoint_to_create_post_no_header() {

        Post post = buildPost(1,"No Header","Test For No Header");
        testBase.response = RestAssured.given()
                .body(post)
                .post(POST_URL);
    }

    @When("I call the endpoint to create post with no body")
    public void i_call_the_endpoint_to_create_post_no_body() {

        testBase.response = RestAssured.given()
                .contentType("application/json")
                .post(POST_URL);
    }

    @And("The post should be successfully created with no body but Id")
    public void thePostShouldBeSuccessfullyCreatedWithNoBodyButId() {

        Post withNoBody = testBase.response.body()
                .as(Post.class);
        assertTrue("",withNoBody.getBody() == null);
        assertTrue("",withNoBody.getTitle() == null);
        assertTrue("",withNoBody.getUserId() == 0);
        assertTrue("",withNoBody.getId() != 0);
    }

}
