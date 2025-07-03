package com.cguillot.insuranceapp.policy.bdd;

import com.cguillot.insuranceapp.policy.bdd.steps.TestContext;
import io.cucumber.junit.platform.engine.Constants;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import java.util.TimeZone;


/*
@RunWith(Cucumber.class)
@CucumberOptions(
        glue = "separated_contexts.features.reporting",
        features = "classpath:separated_contexts/features/reporting")
@CucumberContextConfiguration
@SpringBootTest(classes = PolicySpringTestConfig.class)

 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/policy")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.cguillot.insuranceapp.policy.bdd")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty, json:target/cucumber.json")
// @ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty,html:target/cucumber-reports,json:target/cucumber-reports/cucumber.json")
// @ConfigurationParameter(key = Constants.PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true")
public class RunPolicyCucumberTests {

    @SpringBootTest(
            classes = FeatureBeanDefinitions.class,
            webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
    )
    @ActiveProfiles(value = "features")
    @CucumberContextConfiguration
    static class FeatureSpringConfig {

        @BeforeAll
        static void setupTimeZone() {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        }

    }

    @TestConfiguration
    static class FeatureBeanDefinitions {
        // Bean definitions goes here with @Bean
        // This class could be extracted in its own file

        @Bean
        public TestContext testContext() {
            return new TestContext();
        }

    }
}
