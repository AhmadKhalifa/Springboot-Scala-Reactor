package com.elmenus.checkout.common.exception.badrequest

import com.elmenus.checkout.common.exception.Error
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class OrderTooSmallException(message: String = "Sure you're not that hungry?",
                             errorType: Error.Type = Error.OrderTooSmall,
                             rootException: Throwable = null)
    extends BadRequestException(message, errorType, rootException)
