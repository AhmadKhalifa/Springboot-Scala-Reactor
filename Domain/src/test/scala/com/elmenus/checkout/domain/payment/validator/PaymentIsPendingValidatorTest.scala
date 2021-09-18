package com.elmenus.checkout.domain.payment.validator

import com.elmenus.checkout.common.exception.badrequest.PaymentNotPendingException
import com.elmenus.checkout.domain.item.data.ItemDataService
import com.elmenus.checkout.domain.payment.model.PaymentState
import com.elmenus.checkout.domain.test.utils.{DataFactory, ValidatorTestSuite}
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mock
import reactor.test.StepVerifier

class PaymentIsPendingValidatorTest extends ValidatorTestSuite[PaymentIsPendingValidator] {

    @Mock
    var itemDataService: ItemDataService = _

    @BeforeEach
    def injectMocks(): Unit = validator = new PaymentIsPendingValidator()

    @Test
    def `Given a PENDING payment, when validator validates it, then it should pass the validation`(): Unit = {
        val payment = DataFactory.generatePayment(state = PaymentState.PENDING)

        StepVerifier
            .create(validator.validate(payment))
            .expectNext(payment)
            .verifyComplete()
    }

    @Test
    def `Given a SUCCESS payment, when validator validates it, then a is PaymentNotPendingException thrown`(): Unit = {
        val payment = DataFactory.generatePayment(state = PaymentState.SUCCEEDED)
        val exceptionClass = classOf[PaymentNotPendingException]

        StepVerifier
            .create(validator.validate(payment))
            .verifyError(exceptionClass)
    }
}
