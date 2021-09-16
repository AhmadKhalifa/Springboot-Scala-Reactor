package com.elmenus.checkout.common.exception.technical

import com.elmenus.checkout.common.exception.Error
import com.elmenus.checkout.common.exception.base.ApplicationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class DatabaseException(rootException: Option[Throwable]) extends ApplicationException(
    s"Error accessing database ${if (rootException.isDefined) rootException.get.getMessage else ""}",
    Error.DatabaseException,
    rootException.get
)
