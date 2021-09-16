package com.elmenus.checkout.common.exception.autorization

import com.elmenus.checkout.common.exception.Error
import com.elmenus.checkout.common.exception.base.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class AuthorizationException(message: String = "Unauthorized",
                             errorType: Error.Type = Error.Unauthorized,
                             rootException: Throwable = null)
    extends BusinessException(message, errorType, rootException)
