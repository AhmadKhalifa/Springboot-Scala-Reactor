package com.elmenus.checkout.common.exception.badrequest

import com.elmenus.checkout.common.exception.Error
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class OrderTooLargeException(message: String = "Having a party without me? let's break the order down into smaller orders",
                             errorType: Error.Type = Error.OrderTooLarge,
                             rootException: Throwable = null)
    extends BadRequestException(message, errorType, rootException)
