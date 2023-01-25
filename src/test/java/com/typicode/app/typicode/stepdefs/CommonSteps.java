package com.typicode.app.typicode.stepdefs;

import com.typicode.app.typicode.base.TestBase;
import io.cucumber.java.en.Then;
import static com.typicode.app.typicode.utils.Constants.EMPTY_JSON;
import static org.junit.Assert.assertTrue;

/*
    Class for stepdefs that can be reused across typicode-app endpoints
*/

public class CommonSteps {

    private final TestBase testBase;

    public CommonSteps(TestBase testBase) {
        this.testBase = testBase;
    }

    @Then("The endpoint should return status of {int}")
    public void the_endpoint_should_return_status_of(Integer int1) {

        testBase.response.then().statusCode(int1);
    }

    @Then("I should see an empty body returned by the endpoint")
    public void i_should_see_an_empty_body_returned_by_the_endpoint() {

        testBase.response.then().log().all();
        assertTrue("",testBase.response.getBody().asString().trim().equals(EMPTY_JSON));
    }

}
