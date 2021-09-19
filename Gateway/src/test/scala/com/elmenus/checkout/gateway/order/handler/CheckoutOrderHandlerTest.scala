package com.elmenus.checkout.gateway.order.handler

import com.elmenus.checkout.domain.order.usecase
import com.elmenus.checkout.domain.order.usecase.CheckoutOrderUseCase
import com.elmenus.checkout.gateway.order.mapper.PaymentMapper
import com.elmenus.checkout.gateway.utils.{DataFactory, HandlerFunctionTestSuite}
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.when
import reactor.test.StepVerifier

class CheckoutOrderHandlerTest extends HandlerFunctionTestSuite[CheckoutOrderHandler] {

    @Mock
    var checkoutOrderUseCase: CheckoutOrderUseCase = _

    @Mock
    var paymentMapper: PaymentMapper = _

    @BeforeEach
    def injectMocks(): Unit = {
        handler = new CheckoutOrderHandler(checkoutOrderUseCase, paymentMapper)
        initHandler()
    }

    @Test
    def `Given customer userId, when checkout starts, then a PaymentDTO is returned`(): Unit = {
        val orderCheckoutDto = DataFactory.generateOrderCheckoutDTO()
        val payment = DataFactory.generatePayment()
        val paymentDto = DataFactory.generatePaymentDto()
        val request = requestOf(orderCheckoutDto)

        when(checkoutOrderUseCase.build(any(classOf[usecase.CheckoutOrderUseCase.Parameters]))).thenReturnMono(payment)
        when(paymentMapper.toDto(payment)).thenReturn(paymentDto)

        StepVerifier
            .create(handler.buildPublisher(request))
            .expectNext(paymentDto)
            .verifyComplete()
    }
}
