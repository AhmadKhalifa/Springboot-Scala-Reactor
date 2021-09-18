package com.elmenus.checkout.application.test.cucumber.utils

import org.springframework.stereotype.Component
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

import scala.collection.mutable

@Component
class ScenarioData {

    private val map = new mutable.HashMap[String, Any]()

    def get[T](key: String): T = {
        if (!map.contains(key)) {
            throw new IllegalArgumentException(s"No such key $key")
        }
        if (!map.get(key).isInstanceOf[T]) {
            throw new IllegalArgumentException(s"Value is a ${map(key).getClass.getName}")
        }
        map(key).asInstanceOf[T]
    }

    def set(key: String, value: Any): Unit = map(key) = value

    def getMono[T](key: String): Mono[T] = get[Mono[T]](key)

    def setAuthToken(token: String): Unit = set(ScenarioData.Keys.AUTH_TOKEN, token)

    def getAuthToken: String = map
        .get(ScenarioData.Keys.AUTH_TOKEN)
        .map(_.asInstanceOf[String])
        .getOrElse("")

    def setResponseSpec(responseSpec: WebTestClient.ResponseSpec): Unit =
        set(ScenarioData.Keys.RESPONSE_SPEC, responseSpec)

    def getResponseSpec: WebTestClient.ResponseSpec =
        get[WebTestClient.ResponseSpec](ScenarioData.Keys.RESPONSE_SPEC)

    def clear(): Unit = map.clear()
}

object ScenarioData {
    object Keys {
        val AUTH_TOKEN: String = "AUTH_TOKEN"
        val RESPONSE_SPEC: String = "RESPONSE_SPEC"
    }
}