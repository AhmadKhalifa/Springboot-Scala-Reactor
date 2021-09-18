package com.elmenus.checkout.domain.payment.model.converter

import com.elmenus.checkout.domain.payment.model.PaymentState

import javax.persistence.{AttributeConverter, Converter}

@Converter
class PaymentStatementConverter extends AttributeConverter[PaymentState.Value, String] {

    override def convertToDatabaseColumn(attribute: PaymentState.Value): String = attribute.toString

    override def convertToEntityAttribute(dbData: String): PaymentState.Value = PaymentState.valueOf(dbData)
}
