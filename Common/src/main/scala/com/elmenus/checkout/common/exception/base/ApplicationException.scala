package com.elmenus.checkout.common.exception.base

import com.elmenus.checkout.common.exception.Error

class ApplicationException(message: String,
                           val errorType: Error.Type = Error.InternalServerError,
                           rootException: Throwable = null)
    extends RuntimeException(message, rootException)
