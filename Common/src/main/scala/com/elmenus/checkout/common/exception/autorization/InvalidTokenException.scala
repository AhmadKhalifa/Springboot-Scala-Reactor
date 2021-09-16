package com.elmenus.checkout.common.exception.autorization

import com.elmenus.checkout.common.exception.Error
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class InvalidTokenException(message: String = "Invalid authorization token",
                            errorType: Error.Type = Error.InvalidAuthToken,
                            rootException: Throwable = null)
    extends AuthorizationException(message, errorType, rootException)
