package com.elmenus.checkout.domain.payment.model

object PaymentState extends Enumeration {

    val PENDING, SUCCEEDED: PaymentState.Value = Value

    def isRole(role: String): Boolean = values.exists(_.toString == role)

    def valueOf(state: String): PaymentState.Value = values.filter(_.toString == state.toUpperCase).firstKey
}
