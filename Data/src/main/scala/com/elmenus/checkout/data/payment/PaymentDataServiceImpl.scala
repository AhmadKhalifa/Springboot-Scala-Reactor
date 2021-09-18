package com.elmenus.checkout.data.payment

import com.elmenus.checkout.data.payment.repository.PaymentReactiveRepository
import com.elmenus.checkout.domain.payment.data.PaymentDataService
import com.elmenus.checkout.domain.payment.model.Payment
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PaymentDataServiceImpl(paymentReactiveRepository: PaymentReactiveRepository) extends PaymentDataService {

    override def getByKey(key: String): Mono[Payment] = paymentReactiveRepository.findByKey(key)

    override def save(payment: Payment): Mono[Payment] = paymentReactiveRepository.save(payment)
}
