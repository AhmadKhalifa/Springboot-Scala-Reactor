package com.elmenus.checkout.gateway.order.dto

import com.elmenus.checkout.gateway.order.dto.PaymentUpdatesDTO.Events

import javax.validation.constraints.NotBlank
import scala.beans.BeanProperty

class PaymentUpdatesDTO {

    @NotBlank
    @BeanProperty
    var userId: Long = _

    @NotBlank
    @BeanProperty
    var key: String = _

    @NotBlank
    @BeanProperty
    var event: String = _

    def this(userId: Long, key: String, event: String = Events.SUCCEEDED) = {
        this()
        this.userId = userId
        this.key = key
        this.event = event
    }
}

object PaymentUpdatesDTO {
    object Events {
        val SUCCEEDED: String = "SUCCEEDED"
        val CANCELED: String = "CANCELED"
    }
}
