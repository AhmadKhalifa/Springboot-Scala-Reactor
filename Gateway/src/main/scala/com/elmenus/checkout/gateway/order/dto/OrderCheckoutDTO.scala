package com.elmenus.checkout.gateway.order.dto

import scala.beans.BeanProperty

class OrderCheckoutDTO {

    @BeanProperty
    var userId: Long = _

    def this (userId: Long) = {
        this()
        this.userId = userId
    }
}
