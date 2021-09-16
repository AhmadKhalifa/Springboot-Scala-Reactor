package com.elmenus.checkout.common.exception.badrequest

import com.elmenus.checkout.common.exception.Error
import com.elmenus.checkout.common.exception.base.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(message: String = "Bad request",
                                 errorType: Error.Type = Error.BadRequest,
                                 rootException: Throwable = null)
    extends BusinessException(message, errorType, rootException)
