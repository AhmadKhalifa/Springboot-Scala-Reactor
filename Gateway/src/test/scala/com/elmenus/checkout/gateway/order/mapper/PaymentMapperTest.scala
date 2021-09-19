package com.elmenus.checkout.gateway.order.mapper

import com.elmenus.checkout.gateway.utils.{DataFactory, MapperTestSuite}
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.{BeforeEach, Test}

class PaymentMapperTest extends MapperTestSuite[PaymentMapper] {

    @BeforeEach
    def injectMocks(): Unit = mapper = new PaymentMapper()

    @Test
    def `Given token model object, when mapped using mapper, then a dto credentialsDTO is returned`(): Unit = {
        val payment = DataFactory.generatePayment()

        val mappedPayment = mapper.toDto(payment)

        assertThat(mappedPayment.key).isEqualTo(payment.key)
        assertThat(mappedPayment.amount).isEqualTo(payment.amount)
        assertThat(mappedPayment.state).isEqualTo(payment.state.toString)
    }
}

