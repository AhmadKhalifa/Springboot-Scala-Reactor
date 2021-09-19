package com.elmenus.checkout.gateway.order.dto

import com.elmenus.checkout.domain.payment.model.PaymentState

import scala.beans.BeanProperty

class PaymentDTO {
    @BeanProperty
    var key: String = _

    @BeanProperty
    var amount: Double = _

    @BeanProperty
    var state: PaymentState.Value = PaymentState.PENDING

    def this(key: String, amount: Double, state: PaymentState.Value = PaymentState.PENDING) = {
        this()
        this.key = key
        this.amount = amount
        this.state = state
    }
}
