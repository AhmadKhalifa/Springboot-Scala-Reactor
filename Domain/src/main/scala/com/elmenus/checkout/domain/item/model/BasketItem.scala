package com.elmenus.checkout.domain.item.model

import com.elmenus.checkout.domain.base.BaseEntity

import javax.persistence.{Entity, JoinColumn, OneToOne, Table}
import scala.beans.BeanProperty

@Entity
@Table(name = "basket_items")
class BasketItem extends BaseEntity {

    @BeanProperty
    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    var item: Item = _

    @BeanProperty
    var quantity: Int = 1

    def this(item: Item, quantity: Int = 1) = {
        this()
        this.item = item
        this.quantity = quantity
    }
}
