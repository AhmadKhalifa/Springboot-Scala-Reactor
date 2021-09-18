package com.elmenus.checkout.domain.payment.data

import com.elmenus.checkout.domain.base.BaseDataService
import com.elmenus.checkout.domain.payment.model.{Payment, PaymentState}
import com.elmenus.checkout.domain.order.model.Order
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
trait PaymentDataService extends BaseDataService {

    def getByKey(paymentKey: String): Mono[Payment]

    def save(payment: Payment): Mono[Payment]

    def updatePaymentState(paymentId: Long, paymentState: PaymentState.Value): Mono[Order]
}
