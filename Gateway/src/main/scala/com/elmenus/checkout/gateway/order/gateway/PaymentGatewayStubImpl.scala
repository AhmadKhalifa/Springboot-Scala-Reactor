package com.elmenus.checkout.gateway.order.gateway

import com.elmenus.checkout.domain.payment.gateway.PaymentGateway
import com.elmenus.checkout.domain.payment.model.Payment
import com.elmenus.checkout.domain.payment.usecase.ConfirmPaymentUseCase
import com.elmenus.checkout.gateway.order.dto.{PaymentUpdatesDTO, PaymentUpdatesResponseDTO}
import com.elmenus.checkout.gateway.order.hook.PaymentWebHook
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

import java.util.UUID

@Component
class PaymentGatewayStubImpl(confirmPaymentUseCase: ConfirmPaymentUseCase) extends PaymentGateway with PaymentWebHook {

    override def initializePayment(userId: Long, amount: Double): Mono[Payment] = Mono
        .just(new Payment(UUID.randomUUID().toString, amount))

    override def onEvent(paymentUpdates: PaymentUpdatesDTO): Mono[PaymentUpdatesResponseDTO] = Mono
        .just(paymentUpdates)
        .filter(_.event == PaymentUpdatesDTO.Events.SUCCEEDED)
        .map(updates => ConfirmPaymentUseCase.Parameters(updates.userId, updates.key))
        .flatMap(confirmPaymentUseCase.build)
        .map(_ => new PaymentUpdatesResponseDTO())
}
