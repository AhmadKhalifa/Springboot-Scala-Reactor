package com.elmenus.checkout.common.exception.badrequest

import com.elmenus.checkout.common.exception.Error
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class PaymentNotPendingException(paymentState: String,
                                 errorType: Error.Type = Error.OrderTooSmall,
                                 rootException: Throwable = null)
    extends BadRequestException(s"Cannot update payment as it's in $paymentState state", errorType, rootException)
