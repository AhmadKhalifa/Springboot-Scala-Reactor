package com.elmenus.checkout.domain.order.usecase

import com.elmenus.checkout.domain.base.BaseUseCase
import com.elmenus.checkout.domain.item.data.BasketItemDataService
import com.elmenus.checkout.domain.order.validator.{OrderItemsAvailableValidator, OrderNotTooLargeValidator, OrderNotTooSmallValidator}
import com.elmenus.checkout.domain.payment.data.PaymentDataService
import com.elmenus.checkout.domain.payment.gateway.PaymentGateway
import com.elmenus.checkout.domain.payment.model.Payment
import com.elmenus.checkout.domain.user.data.UserDataService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CheckoutOrderUseCase(userDataService: UserDataService,
                           basketItemDataService: BasketItemDataService,
                           orderItemsAvailableValidator: OrderItemsAvailableValidator,
                           orderNotTooSmallValidator: OrderNotTooSmallValidator,
                           orderNotTooLargeValidator: OrderNotTooLargeValidator,
                           paymentGateway: PaymentGateway,
                           paymentDataService: PaymentDataService)
    extends BaseUseCase[Payment, CheckoutOrderUseCase.Parameters] {

    override def build(params: CheckoutOrderUseCase.Parameters): Mono[Payment] = nonEmpty(params)
        .map(_.userId)
        .flatMap(userId => Mono.zip(
            Mono.just(userId),
            Mono.
                just(userId)
                .flatMap(userDataService.getById)
                .flatMap(basketItemDataService.getAllBasketItemsForUser)
                .validate(
                    orderItemsAvailableValidator,
                    orderNotTooSmallValidator,
                    orderNotTooLargeValidator
                )
                .flatMap(basketItemDataService.calculateSubtotal)
        ))
        .flatMap(userIdAndAmount => paymentGateway.initializePayment(userIdAndAmount.getT1, userIdAndAmount.getT2))
        .flatMap(paymentDataService.save)
}

object CheckoutOrderUseCase {

    case class Parameters(userId: Long)
}
