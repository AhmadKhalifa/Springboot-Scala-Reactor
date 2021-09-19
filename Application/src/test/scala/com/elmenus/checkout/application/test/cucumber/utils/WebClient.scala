package com.elmenus.checkout.application.test.cucumber.utils

import com.elmenus.checkout.application.test.utils.DataFactory
import com.elmenus.checkout.gateway.user.UserRouterConfiguration
import com.elmenus.checkout.gateway.user.dto.TokenDTO
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@Component
class WebClient(webTestClient: WebTestClient, scenarioData: ScenarioData) {

    def authenticate(username: String, password: String): Unit = {
        post(UserRouterConfiguration.Endpoints.AUTH, DataFactory.generateCredentialsDto(username, password))
        responseBody()
            .expectBody(classOf[TokenDTO])
            .consumeWith(results => scenarioData.setAuthToken(results.getResponseBody.getFullToken))
    }

    def get(uri: String): Unit = scenarioData
        .set(
            ScenarioData.Keys.RESPONSE_SPEC,
            webTestClient
                .get()
                .uri(uri)
                .header("Authorization", scenarioData.getAuthToken)
                .exchange()
        )

    def post[T](uri: String, body: T): Unit = scenarioData
        .set(
            ScenarioData.Keys.RESPONSE_SPEC,
            webTestClient
                .post()
                .uri(uri)
                .header("Authorization", scenarioData.getAuthToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(body), classOf[Class[T]])
                .exchange()
        )

    def responseBody(expectedStatusCode: Int = 200): WebTestClient.ResponseSpec = scenarioData
        .get[WebTestClient.ResponseSpec](ScenarioData.Keys.RESPONSE_SPEC)
        .expectStatus()
        .isEqualTo(expectedStatusCode)
}
