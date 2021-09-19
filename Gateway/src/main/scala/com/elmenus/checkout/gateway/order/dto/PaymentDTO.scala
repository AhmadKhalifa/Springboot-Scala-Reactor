package com.elmenus.checkout.gateway.order.dto

import com.elmenus.checkout.domain.payment.model.PaymentState

import scala.beans.BeanProperty

class PaymentDTO {
    @BeanProperty
    var key: String = _

    @BeanProperty
    var amount: Double = _

    @BeanProperty
    var state: String = PaymentState.PENDING.toString

    def this(key: String, amount: Double, state: String = PaymentState.PENDING.toString) = {
        this()
        this.key = key
        this.amount = amount
        this.state = state
    }
}
