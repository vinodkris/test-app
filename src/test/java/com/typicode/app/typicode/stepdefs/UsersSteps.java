package com.typicode.app.typicode.stepdefs;

import com.typicode.app.typicode.base.TestBase;
import com.typicode.app.typicode.msgType.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import java.util.Arrays;
import java.util.List;

import static com.typicode.app.typicode.helper.TypicodeHelper.*;
import static com.typicode.app.typicode.utils.Constants.BASE_URL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Vinod Kris
 */
public class UsersSteps {

    private final TestBase testBase;
    private User user;
    private static final String USERS_URL = BASE_URL + "users";
    private User existingUser;
    private Address address;
    private Company company;

    public UsersSteps(TestBase testBase) {
        this.testBase = testBase;
    }

    @Before()
    public void setUp(){
        address = new Address("stree","suite","city","zip",
                new Geo("123","001"));
        company = new Company("name","catchp","bss");
        user = new User("test user","tester","test@001.com",
                "1234567890","www.test123.com",
                address,company);
    }

    @Given("The endpoint for users exist")
    public void the_endpoint_for_user_exist() {
        RestAssured.given()
                .baseUri(USERS_URL)
                .contentType("application/json");
    }

    @When("I call the endpoint to retrieve users")
    public void i_call_the_endpoint_to_retrieve_users() {
        testBase.response = RestAssured.when().
                get(USERS_URL);
    }

    @Then("I should see the list of users returned by the endpoint")
    public void i_should_see_the_list_of_users_returned_by_the_endpoint() {
        List<User> users = Arrays.asList(testBase.response.getBody().as(User[].class));
        assertTrue("No Users returned",!users.isEmpty());
    }

    @When("I call the endpoint to create users")
    public void i_call_the_endpoint_to_create_users() {
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(user)
                .post(USERS_URL);
    }
    @Then("The user should be successfully created")
    public void the_user_should_be_successfully_created() {
        User actualUser = testBase.response.getBody().as(User.class);
        System.out.println("SETTING ID OF EXPECTED USER TO MATCH THE ACTUAL ONE");
        user.setId(actualUser.getId());
        assertEquals(getJsonNodeFromType(user), getJsonNodeFromType(actualUser));
    }

    @Given("For an existing user")
    public void for_an_existing_user() {
        List<User> users = Arrays.asList(RestAssured
                .when()
                .get(USERS_URL)
                .getBody().as(User[].class));
        existingUser = users.get(0);
    }
    @When("I update the user")
    public void i_update_the_user() {
        user = new User("New test user","updated_tester","test@001.com",
                "1234567890","www.test123.com",
                address,company);
        user.setId(existingUser.getId());
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(user)
                .put(USERS_URL + "/" + user.getId());
    }

    @When("I partially update the user with {string} {string}")
    public void i_partially_update_the_user_with(String fieldToChange, String value) {

        String jsonBody = buildJsonStringWithGivenField(fieldToChange,value);

        user = updateUser(existingUser,fieldToChange,value);
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .patch(USERS_URL + "/" + existingUser.getId());

    }

    @When("I delete an existing user")
    public void i_delete_an_existing_user() {
        List<User> users = Arrays.asList(RestAssured
                .when()
                .get(USERS_URL)
                .getBody().as(User[].class));
        existingUser = users.get(0);
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .delete(USERS_URL + "/" + existingUser.getId());
    }

    @Then("the body should have updated user information")
    public void the_body_should_have_updated_user_information() {

        User updatedUser = testBase.response.getBody().as(User.class);
        assertEquals(getJsonNodeFromType(user), getJsonNodeFromType(updatedUser));

    }

    @When("I call the endpoint to create user with no header")
    public void i_call_the_endpoint_to_create_user_no_header() {
        User userWithNoHeader = new User("New test user","updated_tester","test@001.com",
                "1234567890","www.test123.com",
                address,company);

        testBase.response = RestAssured.given()
                .body(userWithNoHeader)
                .post(USERS_URL);
    }

    @When("I call the endpoint to create user with no body")
    public void i_call_the_endpoint_to_create_no_body() {
        testBase.response = RestAssured.given()
                .header("Content-Type", "application/json")
                .post(USERS_URL);
    }

    @And("The user should be successfully created with no body but Id")
    public void theUserShouldBeSuccessfullyCreatedWithNoBodyButId() {
        User withNoBody = testBase.response.body()
                .as(User.class);
        assertTrue("",withNoBody.getUsername() == null);
        assertTrue("",withNoBody.getEmail() == null);
        assertTrue("",withNoBody.getAddress() == null);
        assertTrue("",withNoBody.getId() != 0);
    }

}
