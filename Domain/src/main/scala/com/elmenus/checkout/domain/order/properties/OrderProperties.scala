package com.elmenus.checkout.domain.order.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import scala.beans.BeanProperty

@Component
@ConfigurationProperties(prefix = "application.order")
class OrderProperties {
    @BeanProperty var minAmount: Int = _
    @BeanProperty var maxAmount: Int = _
}
