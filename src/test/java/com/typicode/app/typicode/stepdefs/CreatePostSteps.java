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
import static com.typicode.app.typicode.utils.Constants.BASE_URL;
import static org.junit.Assert.*;

/**
 * @author Vinod Kris
 */
public class CreatePostSteps {

    private final TestBase testBase;
    public static final String POST_URL = BASE_URL + "posts";
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
    public void i_should_see_the_list_of_posts_returned_by_the_endpoint() {;
        List<Post> posts = Arrays.asList(testBase.response.getBody().as(Post[].class));
        assertTrue("No Posts returned",!posts.isEmpty());
    }

    @When("I call the endpoint to create post with no header")
    public void i_call_the_endpoint_to_create_post() {
        Post post = buildPost("1","No Header","Test For No Header");
        testBase.response = RestAssured.given()
                .body(post)
                .post(POST_URL);
    }

    @When("I call the endpoint to create post with {string} {string} {string}")
    public void i_call_the_endpoint_to_create_post_with(String userId, String title, String body) {
        Post post = buildPost(userId,title,body);
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(post)
                .post(POST_URL);
    }

    @Then("The post should be successfully created with {string} {string} {string}")
    public void the_post_should_be_successfully_created_with(String userId, String title, String body) {
        Post post = testBase.response.getBody().as(Post.class);
        assertTrue("Response userId doesnt match",post.getUserId().equalsIgnoreCase(userId));
        assertTrue("Response title doesnt match",post.getTitle().equalsIgnoreCase(title));
        assertTrue("Response body doesnt match",post.getBody().equalsIgnoreCase(body));
        assertTrue("Id is null",Integer.valueOf(post.getId()) != null);
    }

    @And("The post should be successfully created with no body but Id")
    public void thePostShouldBeSuccessfullyCreatedWithNoBodyButId() {
        Post withNoBody = testBase.response.body()
                .as(Post.class);
        assertTrue("",withNoBody.getBody() == null);
        assertTrue("",withNoBody.getTitle() == null);
        assertTrue("",withNoBody.getUserId() == null);
        assertTrue("",withNoBody.getId() != 0);
    }

    @Given("For an existing post")
    public void for_an_existing_post() {
        List<Post> posts = Arrays.asList(RestAssured
                .when()
                .get(POST_URL)
                .getBody().as(Post[].class));
        existingPost = posts.get(0);
    }

    @When("I update the post with {string} {string} {string}")
    public void i_update_the_post_with(String title, String body, String userId) {
        post = new Post(title,body,userId);
        post.setId(existingPost.getId());
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(post)
                .put(POST_URL + "/" + post.getId());
    }

    @When("I partially update the post with {string} {string}")
    public void i_partially_update_the_post_with(String fieldToChange, String value) {

        String jsonBody = "{\n" +
                "\t\"" + fieldToChange + "\" : \"" + value + "\"\n" +
                "\t\n" +
                "}";

        post = updatePost(existingPost,fieldToChange,value);
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .patch(POST_URL + "/" + existingPost.getId());
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

    @Then("the body should have updated post information")
    public void the_body_should_have_updated_post_information() {
        Post updatedPost = testBase.response.getBody().as(Post.class);
        verifyPost(post,updatedPost);
    }



    private void verifyPost(Post expectedPost, Post actualPost){

        assertTrue("UserId doesnt match",expectedPost.getUserId().equalsIgnoreCase(actualPost.getUserId()));
        assertTrue("Title doesnt match",expectedPost.getTitle().equalsIgnoreCase(actualPost.getTitle()));
        assertTrue("Body doesnt match",expectedPost.getBody().equalsIgnoreCase(actualPost.getBody()));
        assertTrue("Id is null",Integer.valueOf(actualPost.getId()) != null);
    }


}
