package com.elmenus.checkout.gateway.order.handler.factory

import com.elmenus.checkout.gateway.order.handler.CheckoutOrderHandler
import org.springframework.beans.factory.annotation.Lookup
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.{HandlerFunction, ServerRequest, ServerResponse}
import reactor.core.publisher.Mono

@Component
abstract class CheckoutOrderHandlerFactory extends HandlerFunction[ServerResponse]{

    @Lookup
    def checkoutOrderHandler: CheckoutOrderHandler

    override def handle(request: ServerRequest): Mono[ServerResponse] = checkoutOrderHandler.handle(request)
}
