package com.elmenus.checkout.gateway.order.dto

import javax.validation.constraints.NotBlank
import scala.beans.BeanProperty

class OrderCheckoutDTO {

    @NotBlank
    @BeanProperty
    var userId: Long = _

    def this (userId: Long) = {
        this()
        this.userId = userId
    }
}
