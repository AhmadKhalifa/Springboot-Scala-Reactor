package com.elmenus.checkout.domain.payment.data

import com.elmenus.checkout.domain.base.BaseDataService
import com.elmenus.checkout.domain.payment.model.Payment
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
trait PaymentDataService extends BaseDataService {

    def getByKey(paymentKey: String): Mono[Payment]

    def save(payment: Payment): Mono[Payment]
}
