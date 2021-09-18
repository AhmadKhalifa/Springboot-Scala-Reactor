package com.elmenus.checkout.domain.item.model

import com.elmenus.checkout.domain.base.BaseEntity

import javax.persistence.{Convert, Entity, Table}
import scala.beans.BeanProperty

@Entity
@Table(name = "items")
class Item extends BaseEntity {

    @BeanProperty
    var name: String = _

    @BeanProperty
    var price: Double = _

    @BeanProperty
    var available: Boolean = true

    def this(name: String, price: Double, available: Boolean = true) = {
        this()
        this.name = name
        this.price = price
        this.available = available
    }
}
