package com.elmenus.checkout.data.payment.repository

import com.elmenus.checkout.data.test.utils.{DataFactory, ReactiveRepositoryTestSuite}
import com.elmenus.checkout.domain.payment.model.Payment
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mockito.{times, verify, when}
import org.mockito.{ArgumentCaptor, Captor, Mock}
import reactor.test.StepVerifier

import java.util.Optional

class PaymentReactiveRepositoryTest extends ReactiveRepositoryTestSuite[PaymentReactiveRepository] {

    @Captor
    var paymentCaptor: ArgumentCaptor[Payment] = _

    @Mock
    var paymentRepository: PaymentRepository = _

    @BeforeEach
    def injectMocks(): Unit = reactiveRepository = new PaymentReactiveRepository(paymentRepository) {{
        transactionTemplate = PaymentReactiveRepositoryTest.this.transactionTemplate
        jdbcScheduler = PaymentReactiveRepositoryTest.this.jdbcScheduler
    }}

    // findByKey

    @Test
    def `test getByKey. Given existing key, when queried, then the payment is returned`(): Unit = {
        val payment = DataFactory.generatePayment()
        val key = payment.key

        when(paymentRepository.findByKey(key)).thenReturn(Optional.of(payment))

        StepVerifier
            .create(reactiveRepository.findByKey(key))
            .expectNext(payment)
            .verifyComplete()
    }

    @Test
    def `test findByKey. Given non-existing key, when queried, then an empty mono is returned`(): Unit = {
        val payment: Payment = null
        val key = DataFactory.generateString

        when(paymentRepository.findByKey(key)).thenReturn(Optional.ofNullable(payment))

        StepVerifier
            .create(reactiveRepository.findByKey(key))
            .verifyComplete()
    }

    // save

    @Test
    def `test save. Given a payment, when saved to db, then it should return as a response`(): Unit = {
        stubTransactionTemplate()

        val payment = DataFactory.generatePayment()

        when(paymentRepository.save(payment)).thenReturn(payment)

        StepVerifier
            .create(reactiveRepository.save(payment))
            .expectNext(payment)
            .verifyComplete()

        verify(paymentRepository, times(1)).save(paymentCaptor.capture())
        assertThat(paymentCaptor.getValue).isEqualTo(payment)
    }
}
