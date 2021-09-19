package com.elmenus.checkout.gateway.order.gateway

import com.elmenus.checkout.domain.payment.usecase.ConfirmPaymentUseCase
import com.elmenus.checkout.gateway.order.dto.{PaymentUpdatesDTO, PaymentUpdatesResponseDTO}
import com.elmenus.checkout.gateway.utils.{DataFactory, UnitTestSuite}
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{times, verify, when}
import org.mockito.{ArgumentCaptor, Captor, Mock}
import reactor.test.StepVerifier

class PaymentGatewayStubImplTest extends UnitTestSuite {

    @Captor
    var useCaseParametersCaptor: ArgumentCaptor[ConfirmPaymentUseCase.Parameters] = _

    @Mock
    var confirmPaymentUseCase: ConfirmPaymentUseCase = _

    var paymentGatewayStubImpl: PaymentGatewayStubImpl = _

    @BeforeEach
    def injectMocks(): Unit = paymentGatewayStubImpl = new PaymentGatewayStubImpl(confirmPaymentUseCase)

    // initializePayment

    @Test
    def `test initializePayment. Given a userId and amount, when they are sent to payment gateway, a payment intent object is returned` (): Unit = {
        val userId = DataFactory.generateUser().id
        val amount = DataFactory.generateDouble

        StepVerifier
            .create(paymentGatewayStubImpl.initializePayment(userId, amount))
            .expectNextMatches(payment => payment.amount == amount)
    }

    // onEvent

    @Test
    def `test onEvent, Given a SUCCEEDED event, when received, it should submit the order and return an "OK" message response`(): Unit = {
        val paymentUpdate = DataFactory.generatePaymentUpdatesDto(event = PaymentUpdatesDTO.Events.SUCCEEDED)
        val userId = paymentUpdate.userId
        val key = paymentUpdate.key
        val order = DataFactory.generateOrder()
        val expectedCallsCount = 1

        when(confirmPaymentUseCase.build(any(classOf[ConfirmPaymentUseCase.Parameters]))).thenReturnMono(order)

        StepVerifier
            .create(paymentGatewayStubImpl.onEvent(paymentUpdate))
            .expectNextMatches(_.isInstanceOf[PaymentUpdatesResponseDTO])
            .verifyComplete()
        verify(confirmPaymentUseCase, times(expectedCallsCount)).build(useCaseParametersCaptor.capture())
        assertThat(useCaseParametersCaptor.getValue)
            .matches(parameters => parameters.userId == userId && parameters.paymentKey == key)
    }

    @Test
    def `test onEvent, Given a CANCELED event, when received, it should be ignored and empty mono is returned to the server`(): Unit = {
        val paymentUpdate = DataFactory.generatePaymentUpdatesDto(event = PaymentUpdatesDTO.Events.CANCELED)
        val expectedCallsCount = 0

        StepVerifier
            .create(paymentGatewayStubImpl.onEvent(paymentUpdate))
            .verifyComplete()
        verify(confirmPaymentUseCase, times(expectedCallsCount)).build(useCaseParametersCaptor.capture())
    }
}
