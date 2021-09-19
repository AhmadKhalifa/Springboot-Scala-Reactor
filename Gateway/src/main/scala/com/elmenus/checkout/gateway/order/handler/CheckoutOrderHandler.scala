package com.elmenus.checkout.gateway.order.handler

import com.elmenus.checkout.domain.order.usecase.CheckoutOrderUseCase
import com.elmenus.checkout.gateway.base.BaseHandlerFunction
import com.elmenus.checkout.gateway.order.dto.{OrderCheckoutDTO, PaymentDTO}
import com.elmenus.checkout.gateway.order.mapper.PaymentMapper
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono

@Component
@Scope("prototype")
class CheckoutOrderHandler(checkoutOrderUseCase: CheckoutOrderUseCase,
                           paymentMapper: PaymentMapper) extends BaseHandlerFunction[PaymentDTO] {

    override protected def responseClass: Class[PaymentDTO] = classOf[PaymentDTO]

    override protected def responseClassName: String = classOf[PaymentDTO].getName

    override def buildPublisher(request: ServerRequest): Mono[PaymentDTO] =
        requestBody(classOf[OrderCheckoutDTO])
            .map(_.userId)
            .map(CheckoutOrderUseCase.Parameters(_))
            .flatMap(checkoutOrderUseCase.build)
            .map(paymentMapper.toDto)
}
