package com.elmenus.checkout.application.configuration.security.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import scala.beans.BeanProperty

@Component
@ConfigurationProperties(prefix = "application.security.encoder")
class EncoderProperties {
    @BeanProperty var secretKeyFactoryName: String = _
    @BeanProperty var secret: String = _
    @BeanProperty var iteration: Int = _
    @BeanProperty var keyLength: Int = _
}
