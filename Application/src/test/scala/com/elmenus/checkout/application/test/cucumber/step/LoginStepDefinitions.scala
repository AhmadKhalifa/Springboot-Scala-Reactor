package com.elmenus.checkout.application.test.cucumber.step

import com.elmenus.checkout.application.test.cucumber.utils.BaseStepDefinitions
import com.elmenus.checkout.application.test.utils.DataFactory
import com.elmenus.checkout.domain.user.model.{User, UserRole}
import com.elmenus.checkout.gateway.user.UserRouterConfiguration
import io.cucumber.java.After
import io.cucumber.java.en.{Given, Then, When}

class LoginStepDefinitions extends BaseStepDefinitions {

    @After
    def setup(): Unit = {
        scenarioData.clear()
        repositories.foreach(_.deleteAll)
    }

    @Given("a {string} is registered into the application with username {string} and password {string}")
    def aIsRegisteredIntoTheApplicationWithUsernameAndPassword(role: String,
                                                               username: String,
                                                               password: String): Unit = {
        val user = userRepository.findByUsername(username).orElse(new User())
        user.username = username
        user.password = passwordEncoder.encode(password)
        user.role = UserRole.valueOf(role.toUpperCase)
        userRepository.save(user)
    }

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
