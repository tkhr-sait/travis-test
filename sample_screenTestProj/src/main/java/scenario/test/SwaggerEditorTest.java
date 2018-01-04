package scenario.test;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;

import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
public class SwaggerEditorTest {
    @BeforeClass
    public static void before() {
        Configuration.browser = WebDriverRunner.CHROME;
    }
}
