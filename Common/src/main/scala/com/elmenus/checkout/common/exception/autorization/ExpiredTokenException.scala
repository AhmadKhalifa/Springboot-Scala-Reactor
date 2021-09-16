package com.elmenus.checkout.common.exception.autorization

import com.elmenus.checkout.common.exception.Error
import com.elmenus.checkout.common.exception.base.ApplicationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class ExpiredTokenException(message: String = "Authorization token expired",
                            errorType: Error.Type = Error.ExpiredAuthToken,
                            rootException: Throwable = null)
    extends AuthorizationException(message, errorType, rootException)
