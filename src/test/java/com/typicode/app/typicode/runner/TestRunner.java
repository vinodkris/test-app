package com.typicode.app.typicode.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * @author Vinod Kris
 */

@RunWith(Cucumber.class)
@CucumberOptions(

        features="classpath:features/",
        glue={"com.typicode.app.typicode.stepdefs"},
        //tags="@get,@post,@put,@delete,@patch,@options",
//        tags="  @sanity  ",
        plugin = {"pretty",
                "html:target/results.html",
                "json:target/json/results.json",
        },
//        strict=true,
        dryRun=false

)
public class TestRunner {
}
