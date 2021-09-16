package com.elmenus.checkout.application.configuration.validation

import org.springframework.context.annotation.{Bean, Configuration, Primary}
import org.springframework.validation.Validator
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

@Configuration
class ValidationConfiguration {

    @Bean
    @Primary
    def springValidator(): Validator = new LocalValidatorFactoryBean()
}
