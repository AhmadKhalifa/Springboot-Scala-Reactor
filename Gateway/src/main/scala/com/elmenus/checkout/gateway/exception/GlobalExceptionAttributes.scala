package com.elmenus.checkout.gateway.exception

import com.elmenus.checkout.common.exception.base.ApplicationException
import com.elmenus.checkout.gateway.exception.GlobalExceptionAttributes._
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest

@Component
class GlobalExceptionAttributes extends DefaultErrorAttributes {

    override def getErrorAttributes(request: ServerRequest,
                                    options: ErrorAttributeOptions): java.util.Map[String, AnyRef] = {
        val errorResponseMap = super.getErrorAttributes(request, options)
        val requestException = getError(request)
        val exception = if (requestException.isInstanceOf[ApplicationException]) requestException
        else new ApplicationException(
            message = Option(requestException.getMessage).getOrElse(DEFAULT_EXCEPTION_MESSAGE),
//            rootException = exception
        )
        val applicationException = exception.asInstanceOf[ApplicationException]
        val errorType = applicationException.errorType
        errorResponseMap.put(KEY_MESSAGE, Option(exception.getMessage).getOrElse(DEFAULT_EXCEPTION_MESSAGE))
        errorResponseMap.put(KEY_MINOR_CODE, errorType.minorCode)
        errorResponseMap.put(KEY_ERROR_TYPE, errorType.toString)
        errorResponseMap.put(KEY_ROOT_EXCEPTION_MESSAGE, Option(applicationException.getCause).map(_.getMessage).orNull)
        exception match {
            case validationException: ValidationException =>
                errorResponseMap.put(KEY_INVALID_FIELDS, validationException.invalidFields)
            case _ =>
        }
        errorResponseMap
    }
}

object GlobalExceptionAttributes {
    val DEFAULT_EXCEPTION_MESSAGE = "Error processing this request"
    val KEY_STATUS = "status"
    val KEY_MESSAGE = "message"
    val KEY_ROOT_EXCEPTION_MESSAGE = "rootExceptionMessage"
    val KEY_MINOR_CODE = "minorCode"
    val KEY_ERROR_TYPE = "errorType"
    val KEY_INVALID_FIELDS = "invalidFields"
}
