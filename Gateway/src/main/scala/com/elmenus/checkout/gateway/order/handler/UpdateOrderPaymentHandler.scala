package com.elmenus.checkout.gateway.order.handler

import com.elmenus.checkout.gateway.base.BaseHandlerFunction
import com.elmenus.checkout.gateway.order.dto.{PaymentUpdatesDTO, PaymentUpdatesResponseDTO}
import com.elmenus.checkout.gateway.order.hook.PaymentWebHook
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono

@Component
@Scope("prototype")
class UpdateOrderPaymentHandler(paymentWebHook: PaymentWebHook) extends BaseHandlerFunction[PaymentUpdatesResponseDTO] {

    override protected def responseClass: Class[PaymentUpdatesResponseDTO] = classOf[PaymentUpdatesResponseDTO]

    override protected def responseClassName: String = classOf[PaymentUpdatesResponseDTO].getName

    override def buildPublisher(request: ServerRequest): Mono[PaymentUpdatesResponseDTO] =
        requestBody(classOf[PaymentUpdatesDTO])
            .flatMap(paymentWebHook.onEvent)
}
