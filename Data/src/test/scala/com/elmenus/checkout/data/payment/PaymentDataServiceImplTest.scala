package com.elmenus.checkout.data.payment

import com.elmenus.checkout.data.payment.repository.PaymentReactiveRepository
import com.elmenus.checkout.data.test.utils.{DataFactory, DataServiceTestSuite}
import com.elmenus.checkout.domain.payment.model.Payment
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mockito.{times, verify, when}
import org.mockito.{ArgumentCaptor, Captor, Mock}
import reactor.test.StepVerifier

class PaymentDataServiceImplTest extends DataServiceTestSuite[PaymentDataServiceImpl] {

    @Captor
    var paymentCaptor: ArgumentCaptor[Payment] = _

    @Mock
    var paymentReactiveRepository: PaymentReactiveRepository = _

    @BeforeEach
    def injectMocks(): Unit = dataService = new PaymentDataServiceImpl(paymentReactiveRepository)

    // getByKey

    @Test
    def `test getByKey. Given a key for an existing payment, when queried from db, then it should return the payment`(): Unit = {
        val payment = DataFactory.generatePayment()
        val key = payment.key

        when(paymentReactiveRepository.findByKey(key)).thenReturnMono(payment)

        StepVerifier
            .create(dataService.getByKey(key))
            .expectNext(payment)
            .verifyComplete()
    }

    @Test
    def `test getByIdentifier. Given a key for a non-existing payment, when queried from db, then it should return an empty mono`(): Unit = {
        val key = DataFactory.generateString

        when(paymentReactiveRepository.findByKey(key)).thenReturnEmptyMono

        StepVerifier
            .create(dataService.getByKey(key))
            .verifyComplete()
    }

    // save

    @Test
    def `test save. Given a payment, when saved to db, then it should return as a mono response`(): Unit = {
        val payment = DataFactory.generatePayment()

        when(paymentReactiveRepository.save(payment)).thenReturnMono(payment)

        StepVerifier
            .create(paymentReactiveRepository.save(payment))
            .expectNext(payment)
            .verifyComplete()

        verify(paymentReactiveRepository, times(1)).save(paymentCaptor.capture())
        assertThat(paymentCaptor.getValue).isEqualTo(payment)
    }
}
