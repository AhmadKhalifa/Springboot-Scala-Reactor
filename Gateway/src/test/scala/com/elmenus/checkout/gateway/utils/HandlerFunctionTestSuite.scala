package com.elmenus.checkout.gateway.utils

import com.elmenus.checkout.gateway.base.BaseHandlerFunction
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{doNothing, mock, when}
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.validation.Validator
import org.springframework.web.reactive.function.server.ServerRequest

class HandlerFunctionTestSuite[T <: BaseHandlerFunction[_]] extends UnitTestSuite {

    var handler: T = _

    def requestOf[S](body: S): ServerRequest = {
        val request = mock(classOf[ServerRequest])
        ReflectionTestUtils.setField(handler, "request", request)
        when(request.bodyToMono(any(classOf[Class[S]]))).thenReturnMono(body)
        request
    }

    def initHandler(): Unit = {
        val defaultValidator = mock(classOf[Validator])
        doNothing().when(defaultValidator).validate(any(), any())
        ReflectionTestUtils.setField(handler, "defaultValidator", defaultValidator)
    }
}
