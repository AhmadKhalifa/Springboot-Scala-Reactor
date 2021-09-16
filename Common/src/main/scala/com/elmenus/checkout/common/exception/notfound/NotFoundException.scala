package com.elmenus.checkout.common.exception.notfound

import com.elmenus.checkout.common.exception.Error
import com.elmenus.checkout.common.exception.base.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(resourceName: String = "Resource",
                        id: Any = null,
                        errorType: Error.Type = Error.NotFound,
                        rootException: Throwable = null)
    extends BusinessException(s"$resourceName $id not found", errorType, rootException)
