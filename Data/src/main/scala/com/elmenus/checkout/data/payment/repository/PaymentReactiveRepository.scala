package com.elmenus.checkout.data.payment.repository

import com.elmenus.checkout.data.base.ReactiveJpaRepository
import com.elmenus.checkout.domain.payment.model.Payment
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class PaymentReactiveRepository(paymentRepository: PaymentRepository)
    extends ReactiveJpaRepository[Payment, PaymentRepository] {

    override protected def repository: PaymentRepository = paymentRepository

    def findByKey(key: String): Mono[Payment] = Mono
        .just(key)
        .flatMap(username => Mono.defer(() => Mono.fromCallable(() => repository.findByKey(username))))
        .flatMap(optional => if (optional.isPresent) Mono.just(optional.get()) else Mono.empty())
        .subscribeOn(jdbcScheduler)
}
