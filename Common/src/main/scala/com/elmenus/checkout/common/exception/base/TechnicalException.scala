package com.elmenus.checkout.common.exception.base

import com.elmenus.checkout.common.exception.Error
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class TechnicalException(message: String = "Something went wrong",
                         errorType: Error.Type = Error.InternalServerError,
                         rootException: Throwable = null)
    extends ApplicationException(message, errorType, rootException)
