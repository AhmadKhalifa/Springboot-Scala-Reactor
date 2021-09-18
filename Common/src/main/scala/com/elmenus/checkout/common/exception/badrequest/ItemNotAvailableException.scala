package com.elmenus.checkout.common.exception.badrequest

import com.elmenus.checkout.common.exception.Error
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ItemNotAvailableException(message: String = "Sorry, one or more items are not available, let's try something else",
                                errorType: Error.Type = Error.ItemNotAvailable,
                                rootException: Throwable = null)
    extends BadRequestException(
        message,
        errorType,
        rootException
    )
