package com.elmenus.checkout.domain.authentication.usecase

import com.elmenus.checkout.domain.authentication.data.AuthenticationService
import com.elmenus.checkout.domain.authentication.model.JwtAuthentication
import com.elmenus.checkout.domain.authentication.validator.{TokenExistsValidator, TokenExpiryValidator, TokenUserValidator}
import com.elmenus.checkout.domain.base.BaseUseCase
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthenticateUseCase(authenticatorDataService: AuthenticationService,
                          tokenExistsValidator: TokenExistsValidator,
                          tokenExpiryValidator: TokenExpiryValidator,
                          tokenUserValidator: TokenUserValidator)
    extends BaseUseCase[JwtAuthentication, AuthenticateUseCase.Parameters] {

    override def build(params: AuthenticateUseCase.Parameters): Mono[JwtAuthentication] = nonEmpty(params)
        .map(_.token)
        .validate(tokenExistsValidator, tokenExpiryValidator, tokenUserValidator)
        .flatMap(authenticatorDataService.authenticate)
}

object AuthenticateUseCase {

    case class Parameters(token: String)
}
