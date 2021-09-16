package com.elmenus.checkout.application.configuration.security.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import scala.beans.BeanProperty

@Component
@ConfigurationProperties(prefix = "application.security")
class SecurityProperties {
    @BeanProperty var permittedEndpoints: Array[String] = _
}
