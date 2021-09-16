package com.elmenus.checkout.common.exception.notfound

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException(userIdentifier: Any,
                            rootException: Throwable = null)
    extends NotFoundException("User", userIdentifier, rootException = rootException)
