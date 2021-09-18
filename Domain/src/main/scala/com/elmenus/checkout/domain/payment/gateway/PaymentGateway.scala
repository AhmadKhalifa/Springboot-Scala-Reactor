package com.elmenus.checkout.domain.payment.gateway

import com.elmenus.checkout.domain.payment.model.Payment
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
trait PaymentGateway {

    def initializePayment(userId: Long, amount: Double): Mono[Payment]
}
