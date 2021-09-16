package com.elmenus.checkout.common.exception.badrequest

import com.elmenus.checkout.common.exception.Error
import com.elmenus.checkout.common.exception.base.{ApplicationException, BusinessException}
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class IncorrectPasswordException(message: String = "Incorrect password",
                                 errorType: Error.Type = Error.IncorrectPassword,
                                 rootException: Throwable = null)
    extends BadRequestException(message, errorType, rootException)
