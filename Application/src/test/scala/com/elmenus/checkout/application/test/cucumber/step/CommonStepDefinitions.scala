package com.elmenus.checkout.application.test.cucumber.step

import com.elmenus.checkout.application.test.cucumber.utils.BaseStepDefinitions
import com.elmenus.checkout.application.test.cucumber.utils.ScenarioData.Keys
import com.elmenus.checkout.domain.user.model.{User, UserRole}
import io.cucumber.java.After
import io.cucumber.java.en.{And, Given, Then}
import org.awaitility.Awaitility.await
import org.awaitility.Duration

class CommonStepDefinitions extends BaseStepDefinitions {

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

    @And("customer {string} is signed in with password {string}")
    def customerIsSignedIn(username: String, password: String): Unit = {
        webClient.authenticate(username, password)
        await()
            .atMost(Duration.FIVE_SECONDS)
            .until(() => scenarioData.getAuthToken.nonEmpty)
        val user = userRepository.findByUsername(username).get()
        scenarioData.set(Keys.CURRENT_USER, user)
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
