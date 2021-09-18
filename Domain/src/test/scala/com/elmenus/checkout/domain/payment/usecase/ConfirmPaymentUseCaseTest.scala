package com.elmenus.checkout.domain.payment.usecase

import com.elmenus.checkout.common.exception.badrequest.PaymentNotPendingException
import com.elmenus.checkout.domain.order.data.OrderDataService
import com.elmenus.checkout.domain.payment.data.PaymentDataService
import com.elmenus.checkout.domain.payment.model.PaymentState
import com.elmenus.checkout.domain.payment.validator.PaymentIsPendingValidator
import com.elmenus.checkout.domain.test.utils.{DataFactory, UseCaseTestSuite}
import com.elmenus.checkout.domain.user.data.UserDataService
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mock
import org.mockito.Mockito.when
import reactor.test.StepVerifier

class ConfirmPaymentUseCaseTest extends UseCaseTestSuite[ConfirmPaymentUseCase] {

    @Mock
    var userDataService: UserDataService = _

    @Mock
    var paymentDataService: PaymentDataService = _

    @Mock
    var paymentIsPendingValidator: PaymentIsPendingValidator = _

    @Mock
    var orderDataService: OrderDataService = _

    @BeforeEach
    def injectMocks(): Unit = useCase = new ConfirmPaymentUseCase(
        userDataService,
        paymentDataService,
        paymentIsPendingValidator,
        orderDataService
    )

    @Test
    def `Given a pending payment, when a confirmation is received from payment gateway web hook, then a payment is marked as SUCCESSFUL and order is submitted`(): Unit = {
        val user = DataFactory.generateUser
        val userId = user.id
        val payment = DataFactory.generatePayment()
        val paymentKey = payment.key
        val order = DataFactory.generateOrder(user, payment)

        when(userDataService.getById(userId)).thenReturnMono(user)
        when(paymentDataService.getByKey(paymentKey)).thenReturnMono(payment)
        when(paymentIsPendingValidator.validate(payment)).thenReturnMono(payment)
        when(paymentDataService.save(payment)).thenReturnMono(payment)
        when(orderDataService.submitOrder(user, payment)).thenReturnMono(order)

        StepVerifier
            .create(useCase.build(ConfirmPaymentUseCase.Parameters(userId, paymentKey)))
            .expectNext(order)
            .verifyComplete()
    }

    @Test
    def `Given a non-pending payment, when a confirmation is received from payment gateway web hook, then a PaymentNotPendingException is thrown`(): Unit = {
        val user = DataFactory.generateUser
        val userId = user.id
        val payment = DataFactory.generatePayment(state = PaymentState.SUCCEEDED)
        val paymentKey = payment.key
        val exception = new PaymentNotPendingException(payment.state.toString)

        when(userDataService.getById(userId)).thenReturnMono(user)
        when(paymentDataService.getByKey(paymentKey)).thenReturnMono(payment)
        when(paymentIsPendingValidator.validate(payment)).thenReturnErrorMono(exception)

        StepVerifier
            .create(useCase.build(ConfirmPaymentUseCase.Parameters(userId, paymentKey)))
            .verifyErrorMatches(_ equals exception)
    }
}
