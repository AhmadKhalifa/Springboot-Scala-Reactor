package com.elmenus.checkout.gateway.order.dto

import scala.beans.BeanProperty

class PaymentUpdatesResponseDTO {

    @BeanProperty
    var message: String = "OK"

    def this(message: String = "OK") = {
        this()
        this.message = message
    }
}
