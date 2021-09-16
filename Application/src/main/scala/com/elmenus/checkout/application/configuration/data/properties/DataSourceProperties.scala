package com.elmenus.checkout.application.configuration.data.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import scala.beans.BeanProperty

@Component
@ConfigurationProperties(prefix = "spring.datasource.hikari")
class DataSourceProperties {
    @BeanProperty var maximumPoolSize: Int = _
}
