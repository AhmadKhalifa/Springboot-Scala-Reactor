package com.elmenus.checkout.domain.payment.validator

import com.elmenus.checkout.common.exception.badrequest.PaymentNotPendingException
import com.elmenus.checkout.common.exception.base.BusinessException
import com.elmenus.checkout.domain.base.BaseValidator
import com.elmenus.checkout.domain.payment.model.{Payment, PaymentState}
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class PaymentIsPendingValidator extends BaseValidator[Payment] {

    override def isValid(data: Payment): Mono[Boolean] = Mono
        .just(data)
        .map(_.state == PaymentState.PENDING)

    override def getValidationErrorException(data: Payment): BusinessException =
        new PaymentNotPendingException(data.state.toString)
}
