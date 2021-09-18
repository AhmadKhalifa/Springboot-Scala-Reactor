package com.elmenus.checkout.domain.order.model

import com.elmenus.checkout.domain.base.BaseEntity
import com.elmenus.checkout.domain.payment.model.Payment
import com.elmenus.checkout.domain.user.model.User

import javax.persistence.{Entity, JoinColumn, ManyToOne, OneToOne, Table}
import scala.beans.BeanProperty

@Entity
@Table(name = "orders")
class Order extends BaseEntity {

    @BeanProperty
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User = _

    @BeanProperty
    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    var payment: Payment = _

    def this(user: User, payment: Payment) = {
        this()
        this.user = user
        this.payment = payment
    }
}
