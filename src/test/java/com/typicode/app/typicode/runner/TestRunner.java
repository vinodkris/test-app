package com.typicode.app.typicode.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/*
    Runner class to run stepdefs
 */

@RunWith(Cucumber.class)
@CucumberOptions(

        features="classpath:features/",
        glue={"com.typicode.app.typicode.stepdefs"},
        plugin = {"pretty",
                "html:target/results.html",
                "json:target/json/results.json",
        },
        dryRun=false

)
public class TestRunner {
}
