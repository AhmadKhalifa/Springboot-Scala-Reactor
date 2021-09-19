package com.elmenus.checkout.application.test.cucumber.step

import com.elmenus.checkout.application.test.cucumber.utils.BaseStepDefinitions
import com.elmenus.checkout.application.test.utils.DataFactory
import com.elmenus.checkout.gateway.user.UserRouterConfiguration
import io.cucumber.java.en.{Then, When}

class LoginStepDefinitions extends BaseStepDefinitions {

    @When("a user tries to login with credentials {string} as username and {string} as password")
    def aUserTriesToLoginWithCredentialsAsUsernameAndAsPassword(username: String, password: String): Unit = {
        webClient.post(
            UserRouterConfiguration.Endpoints.AUTH,
            DataFactory.generateCredentialsDto(username, password)
        )
    }

    @Then("a token is returned to the user as response")
    def aTokenIsReturnedToTheUserAsResponse(): Unit = {
        webClient
            .responseBody()
            .expectBody()
                .jsonPath("$.token").isNotEmpty
                .jsonPath("$.expirationDate").isNotEmpty
                .jsonPath("$.type").isEqualTo("Bearer")
    }
}
