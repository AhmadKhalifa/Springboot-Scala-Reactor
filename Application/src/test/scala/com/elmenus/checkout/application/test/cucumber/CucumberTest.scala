package com.elmenus.checkout.application.test.cucumber

import io.cucumber.junit.{Cucumber, CucumberOptions}
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(
    features = Array("src/test/resources/features"),
    glue = Array("com.elmenus.checkout.application.test.cucumber.step")
)
trait CucumberTest
