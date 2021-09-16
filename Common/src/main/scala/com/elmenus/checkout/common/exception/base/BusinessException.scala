package com.elmenus.checkout.common.exception.base

import com.elmenus.checkout.common.exception.Error
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BusinessException(message: String = "Something went wrong",
                        errorType: Error.Type = Error.BadRequest,
                        rootException: Throwable = null)
    extends ApplicationException(message, errorType, rootException)
