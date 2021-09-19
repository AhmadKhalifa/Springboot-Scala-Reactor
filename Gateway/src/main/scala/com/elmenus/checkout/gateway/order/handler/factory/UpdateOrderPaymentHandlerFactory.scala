package com.elmenus.checkout.gateway.order.handler.factory

import com.elmenus.checkout.gateway.order.handler.UpdateOrderPaymentHandler
import org.springframework.beans.factory.annotation.Lookup
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.{HandlerFunction, ServerRequest, ServerResponse}
import reactor.core.publisher.Mono

@Component
abstract class UpdateOrderPaymentHandlerFactory extends HandlerFunction[ServerResponse]{

    //noinspection MutatorLikeMethodIsParameterless
    @Lookup
    def updateOrderPaymentHandler: UpdateOrderPaymentHandler

    override def handle(request: ServerRequest): Mono[ServerResponse] = updateOrderPaymentHandler.handle(request)
}
