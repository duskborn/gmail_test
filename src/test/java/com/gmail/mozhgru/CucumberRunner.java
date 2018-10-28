package com.gmail.mozhgru;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;


@CucumberOptions(
        strict = true,
        features = {"src/test/resources/features"},
        glue = "com.gmail.mozhgru.stepdefinition",
        tags = "@test"
)

public class CucumberRunner extends AbstractTestNGCucumberTests {
}
