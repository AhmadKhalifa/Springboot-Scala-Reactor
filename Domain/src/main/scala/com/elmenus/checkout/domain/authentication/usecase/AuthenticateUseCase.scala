package com.elmenus.checkout.domain.authentication.usecase

import com.elmenus.checkout.domain.authentication.data.AuthenticationService
import com.elmenus.checkout.domain.authentication.model.JwtAuthentication
import com.elmenus.checkout.domain.authentication.validator.{TokenExistenceValidator, TokenNotExpiredValidator, TokenUserValidator}
import com.elmenus.checkout.domain.base.BaseUseCase
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthenticateUseCase(authenticationService: AuthenticationService,
                          tokenExistenceValidator: TokenExistenceValidator,
                          tokenNotExpiredValidator: TokenNotExpiredValidator,
                          tokenUserValidator: TokenUserValidator)
    extends BaseUseCase[JwtAuthentication, AuthenticateUseCase.Parameters] {

    override def build(params: AuthenticateUseCase.Parameters): Mono[JwtAuthentication] = nonEmpty(params)
        .map(_.token)
        .validate(tokenExistenceValidator, tokenNotExpiredValidator, tokenUserValidator)
        .flatMap(authenticationService.authenticate)
}

object AuthenticateUseCase {

    case class Parameters(token: String)
}
