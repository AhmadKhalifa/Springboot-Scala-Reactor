package com.elmenus.checkout.common.exception.badrequest

import com.elmenus.checkout.common.exception.Error
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidParameterException(message: String = "Invalid or missing parameter",
                                errorType: Error.Type = Error.InvalidBody,
                                rootException: Throwable = null)
    extends BadRequestException(message, errorType, rootException)
