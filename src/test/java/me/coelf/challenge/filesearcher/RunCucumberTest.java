package me.coelf.challenge.filesearcher;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty" },
        features = "src/test/resources",
        strict = true)
public class RunCucumberTest
{
}
