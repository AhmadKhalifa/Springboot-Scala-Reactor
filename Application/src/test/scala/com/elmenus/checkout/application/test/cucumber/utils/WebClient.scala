package com.elmenus.checkout.application.test.cucumber.utils

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@Component
class WebClient(webTestClient: WebTestClient, scenarioData: ScenarioData) {

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
