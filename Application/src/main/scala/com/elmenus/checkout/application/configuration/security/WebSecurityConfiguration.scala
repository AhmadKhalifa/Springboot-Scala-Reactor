package com.elmenus.checkout.application.configuration.security

import com.elmenus.checkout.application.configuration.security.properties.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.http.{HttpMethod, HttpStatus}
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import reactor.core.publisher.Mono

import scala.jdk.CollectionConverters.IterableHasAsJava

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebSecurityConfiguration(authenticationManager: AuthenticationManager,
                               securityContextRepository: ServerSecurityContextRepository,
                               securityProperties: SecurityProperties) {

    @Bean
    def securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain = http
        .exceptionHandling()
        .authenticationEntryPoint((serverWebExchange, _) =>
            Mono.fromRunnable(() => serverWebExchange.getResponse.setStatusCode(HttpStatus.UNAUTHORIZED))
        )
        .accessDeniedHandler((serverWebExchange, _) =>
            Mono.fromRunnable(() => serverWebExchange.getResponse.setStatusCode(HttpStatus.FORBIDDEN))
        )
        .and()
        .csrf().disable()
        .formLogin().disable()
        .httpBasic().disable()
        .authenticationManager(authenticationManager)
        .securityContextRepository(securityContextRepository)
        .authorizeExchange()
        .pathMatchers(HttpMethod.OPTIONS).permitAll()
        .pathMatchers(securityProperties.permittedEndpoints:_*).permitAll()
        .anyExchange().authenticated()
        .and().build()
}
