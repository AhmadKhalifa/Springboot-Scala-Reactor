package com.elmenus.checkout.gateway.utils

import com.elmenus.checkout.gateway.base.BaseHandlerFunction
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{mock, when}
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.web.reactive.function.server.ServerRequest

class HandlerFunctionTestSuite[T <: BaseHandlerFunction[_]] extends UnitTestSuite {

    var handler: T = _

    def requestOf[S](body: S): ServerRequest = {
        val request = mock(classOf[ServerRequest])
        ReflectionTestUtils.setField(handler, "request", request)
        when(request.bodyToMono(any(classOf[Class[S]]))).thenReturnMono(body)
        request
    }
}
