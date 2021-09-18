package com.elmenus.checkout.application.test.cucumber.step

import com.elmenus.checkout.application.test.cucumber.utils.BaseStepDefinitions
import io.cucumber.java.After
import io.cucumber.java.en.Then

class CommonStepDefinitions extends BaseStepDefinitions {

    @After
    def setup(): Unit = {
        scenarioData.clear()
        repositories.foreach(_.deleteAll)
    }

    @Then("an error is returned to the user with status code {int}, minor code {int}, and a message {string}")
    def anErrorIsReturnedToTheUserWithStatusCodeAndMinorCodeWithAMessageContains(statusCode: Int,
                                                                                 minorCode: Int,
                                                                                 errorMessage: String): Unit = {
        webClient
            .responseBody(statusCode)
            .expectBody()
                .jsonPath("$.minorCode").isEqualTo(minorCode)
                .jsonPath("$.message").isEqualTo(errorMessage)
    }
}
