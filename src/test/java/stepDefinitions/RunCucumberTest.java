package stepDefinitions;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(publish = false
        , features = "src/test/resources/features"
        , glue = "stepDefinitions"
        , snippets = SnippetType.CAMELCASE)

public class RunCucumberTest {
}