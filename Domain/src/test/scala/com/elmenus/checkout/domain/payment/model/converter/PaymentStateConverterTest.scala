package com.elmenus.checkout.domain.payment.model.converter

import com.elmenus.checkout.domain.payment.model.PaymentState
import com.elmenus.checkout.domain.test.utils.UnitTestSuite
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.{BeforeEach, Test}

class PaymentStateConverterTest extends UnitTestSuite {

    var paymentStateConverter: PaymentStatementConverter = _

    @BeforeEach
    def injectMocks(): Unit = paymentStateConverter = new PaymentStatementConverter()

    private def testFromDatabaseColumnToEntityAttribute(attribute: PaymentState.Value): Unit = {
        assertThat(paymentStateConverter.convertToDatabaseColumn(attribute)).isEqualTo(attribute.toString)
    }

    private def testFromEntityAttributeToDatabaseColumn(attribute: PaymentState.Value): Unit = {
        assertThat(paymentStateConverter.convertToEntityAttribute(attribute.toString).id).isEqualTo(attribute.id)
    }

    @Test
    def `Given PENDING as enum, when its converted to database column, then it should be "PENDING" as string`(): Unit = {
        testFromEntityAttributeToDatabaseColumn(PaymentState.PENDING)
    }

    @Test
    def `Given SUCCEEDED as enum, when its converted to database column, then it should be "SUCCEEDED" as string`(): Unit = {
        testFromEntityAttributeToDatabaseColumn(PaymentState.SUCCEEDED)
    }

    @Test
    def `Given "PENDING" as String, when its converted to entity attribute, then it should be PENDING as enum`(): Unit = {
        testFromDatabaseColumnToEntityAttribute(PaymentState.PENDING)
    }

    @Test
    def `Given "SUCCEEDED" as String, when its converted to entity attribute, then it should be SUCCEEDED as enum`(): Unit = {
        testFromDatabaseColumnToEntityAttribute(PaymentState.SUCCEEDED)
    }
}
