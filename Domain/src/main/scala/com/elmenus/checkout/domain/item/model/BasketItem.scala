package com.elmenus.checkout.domain.item.model

import com.elmenus.checkout.domain.base.BaseEntity
import com.elmenus.checkout.domain.user.model.User

import javax.persistence.{Entity, FetchType, JoinColumn, ManyToOne, OneToOne, Table}
import scala.beans.BeanProperty

@Entity
@Table(name = "basket_items")
class BasketItem extends BaseEntity {

    @BeanProperty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User = _

    @BeanProperty
    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    var item: Item = _

    @BeanProperty
    var quantity: Int = 1

    def this(user: User, item: Item, quantity: Int = 1) = {
        this()
        this.user = user
        this.item = item
        this.quantity = quantity
    }
}
