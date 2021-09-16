package com.elmenus.checkout.application.configuration.security

import com.elmenus.checkout.common.exception.autorization.MissingTokenException
import com.elmenus.checkout.domain.authentication.usecase.AuthenticateUseCase
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthenticationManager(authenticateUseCase: AuthenticateUseCase) extends ReactiveAuthenticationManager {

    override def authenticate(authentication: Authentication): Mono[Authentication] = Mono
        .justOrEmpty(authentication.getCredentials.toString)
        .switchIfEmpty(Mono.defer(() => Mono.error(new MissingTokenException())))
        .map(AuthenticateUseCase.Parameters(_))
        .flatMap(authenticateUseCase.build)
}
