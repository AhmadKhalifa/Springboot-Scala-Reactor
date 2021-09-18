package com.elmenus.checkout.domain.payment.model

import com.elmenus.checkout.domain.base.BaseEntity
import com.elmenus.checkout.domain.payment.model.converter.PaymentStatementConverter

import javax.persistence.{Convert, Entity, Table}
import scala.beans.BeanProperty

@Entity
@Table(name = "payments")
class Payment extends BaseEntity {

    @BeanProperty
    var key: String = _

    @BeanProperty
    var amount: Double = _

    @BeanProperty
    @Convert(converter = classOf[PaymentStatementConverter])
    var state: PaymentState.Value = PaymentState.PENDING

    def this(key: String, amount: Double, state: PaymentState.Value = PaymentState.PENDING) = {
        this()
        this.key = key
        this.amount = amount
        this.state = state
    }
}
