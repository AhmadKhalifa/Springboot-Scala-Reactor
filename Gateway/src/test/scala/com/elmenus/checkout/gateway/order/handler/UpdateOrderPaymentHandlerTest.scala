package com.elmenus.checkout.gateway.order.handler

import com.elmenus.checkout.gateway.order.hook.PaymentWebHook
import com.elmenus.checkout.gateway.utils.{DataFactory, HandlerFunctionTestSuite}
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mock
import org.mockito.Mockito.when
import reactor.test.StepVerifier

class UpdateOrderPaymentHandlerTest extends HandlerFunctionTestSuite[UpdateOrderPaymentHandler] {

    @Mock
    var paymentWebHook: PaymentWebHook = _

    @BeforeEach
    def injectMocks(): Unit = {
        handler = new UpdateOrderPaymentHandler(paymentWebHook)
        initHandler()
    }

    @Test
    def `Given customer userId, when checkout starts, then a PaymentDTO is returned`(): Unit = {
        val paymentUpdates = DataFactory.generatePaymentUpdatesDto()
        val response = DataFactory.generatePaymentUpdatesResponseDTO
        val request = requestOf(paymentUpdates)

        when(paymentWebHook.onEvent(paymentUpdates)).thenReturnMono(response)

        StepVerifier
            .create(handler.buildPublisher(request))
            .expectNext(response)
            .verifyComplete()
    }
}
