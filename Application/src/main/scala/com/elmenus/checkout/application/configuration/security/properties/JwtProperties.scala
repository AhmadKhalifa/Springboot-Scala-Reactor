package com.elmenus.checkout.application.configuration.security.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import scala.beans.BeanProperty

@Component
@ConfigurationProperties(prefix = "application.security.jwt")
class JwtProperties {
    @BeanProperty var secret: String = _
    @BeanProperty var expiration: Long = _
}
