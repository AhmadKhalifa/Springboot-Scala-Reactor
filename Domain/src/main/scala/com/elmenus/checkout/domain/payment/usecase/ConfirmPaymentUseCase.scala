package com.elmenus.checkout.domain.payment.usecase

import com.elmenus.checkout.domain.base.BaseUseCase
import com.elmenus.checkout.domain.payment.data.PaymentDataService
import com.elmenus.checkout.domain.payment.model.PaymentState
import com.elmenus.checkout.domain.payment.validator.PaymentIsPendingValidator
import com.elmenus.checkout.domain.item.data.OrderDataService
import com.elmenus.checkout.domain.order.model.Order
import com.elmenus.checkout.domain.user.data.UserDataService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ConfirmPaymentUseCase(userDataService: UserDataService,
                            paymentDataService: PaymentDataService,
                            paymentIsPendingValidator: PaymentIsPendingValidator,
                            orderDataService: OrderDataService)
    extends BaseUseCase[Order, ConfirmPaymentUseCase.Parameters] {

    override def build(params: ConfirmPaymentUseCase.Parameters): Mono[Order] = nonEmpty(params)
        .flatMap(params =>
            Mono.zip(
                Mono
                    .just(params.userId)
                    .flatMap(userDataService.getById),
                Mono
                    .just(params.paymentKey)
                    .flatMap(paymentDataService.getByKey)
                    .validate(paymentIsPendingValidator)
                    .doOnNext(_.state = PaymentState.SUCCEEDED)
                    .flatMap(paymentDataService.save)
            )
        )
        .flatMap(userAndPayment => orderDataService.submitOrder(userAndPayment.getT1, userAndPayment.getT2))
}

object ConfirmPaymentUseCase {

    case class Parameters(userId: Long, paymentKey: String)
}
