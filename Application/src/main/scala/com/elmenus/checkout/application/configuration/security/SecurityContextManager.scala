package com.elmenus.checkout.application.configuration.security

import com.elmenus.checkout.common.exception.autorization.{AuthorizationException, MissingTokenException}
import com.elmenus.checkout.domain.authentication.model.Token.AUTH_HEADER_PREFIX
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.{SecurityContext, SecurityContextImpl}
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SecurityContextManager(authenticationManager: AuthenticationManager)
    extends ServerSecurityContextRepository {

    override def save(exchange: ServerWebExchange, context: SecurityContext): Mono[Void] =
        throw new UnsupportedOperationException("Not supported")

    override def load(exchange: ServerWebExchange): Mono[SecurityContext] = Mono
        .justOrEmpty(exchange)
        .switchIfEmpty(Mono.defer(() => Mono.error(new AuthorizationException())))
        .flatMap(webExchange => {
            val tokenOption = Option(webExchange.getRequest.getHeaders.getFirst(HttpHeaders.AUTHORIZATION))
            val token = if (tokenOption.isDefined) tokenOption.get else ""
            if (token.nonEmpty && token.startsWith(AUTH_HEADER_PREFIX)) Mono.just(token)
            else Mono.error(new MissingTokenException())
        })
        .map(token => new UsernamePasswordAuthenticationToken(token, token))
        .flatMap(authenticationManager.authenticate)
        .map(new SecurityContextImpl(_))
}
