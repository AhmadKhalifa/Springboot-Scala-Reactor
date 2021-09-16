package com.elmenus.checkout.application.configuration.data

import com.elmenus.checkout.application.configuration.data.properties.DataSourceProperties
import org.springframework.context.annotation.{Bean, Configuration}
import reactor.core.scheduler.{Scheduler, Schedulers}

import java.util.concurrent.Executors

@Configuration
class DataSourceConfiguration {

    @Bean
    def jdbcScheduler(dataSourceProperties: DataSourceProperties): Scheduler = Schedulers
        .fromExecutor(Executors.newFixedThreadPool(dataSourceProperties.maximumPoolSize))
}
