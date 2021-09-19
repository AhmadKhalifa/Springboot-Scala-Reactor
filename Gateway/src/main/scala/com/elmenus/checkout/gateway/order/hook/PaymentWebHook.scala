package com.elmenus.checkout.gateway.order.hook

import com.elmenus.checkout.gateway.order.dto.{PaymentUpdatesDTO, PaymentUpdatesResponseDTO}
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
trait PaymentWebHook {

    def onEvent(paymentUpdates: PaymentUpdatesDTO): Mono[PaymentUpdatesResponseDTO]
}
