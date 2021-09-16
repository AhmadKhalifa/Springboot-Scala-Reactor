package com.elmenus.checkout.gateway.business.user.router.handler

import org.springframework.beans.factory.annotation.Lookup
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.{HandlerFunction, ServerRequest, ServerResponse}
import reactor.core.publisher.Mono

@Component
abstract class LoginHandlerFactory extends HandlerFunction[ServerResponse]{

    @Lookup
    def loginHandler: LoginHandler

    override def handle(request: ServerRequest): Mono[ServerResponse] = loginHandler.handle(request)
}
