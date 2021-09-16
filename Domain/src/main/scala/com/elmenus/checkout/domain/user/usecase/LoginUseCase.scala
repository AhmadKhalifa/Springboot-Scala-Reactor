package com.elmenus.checkout.domain.user.usecase

import com.elmenus.checkout.domain.authentication.data.AuthenticationService
import com.elmenus.checkout.domain.authentication.model.Token
import com.elmenus.checkout.domain.base.BaseUseCase
import com.elmenus.checkout.domain.user.data.UserDataService
import com.elmenus.checkout.domain.user.model.Credentials
import com.elmenus.checkout.domain.user.validator.{CorrectPasswordValidator, UserExistsValidator}
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class LoginUseCase(userDataService: UserDataService,
                   authenticationService: AuthenticationService,
                   userExistsValidator: UserExistsValidator,
                   correctPasswordValidator: CorrectPasswordValidator)
    extends BaseUseCase[Token, LoginUseCase.Parameters] {

    override def build(params: LoginUseCase.Parameters): Mono[Token] = nonEmpty(params)
        .map(_.credentials)
        .validate(userExistsValidator)
        .validate(correctPasswordValidator)
        .map(_.username)
        .flatMap(userDataService.getByIdentifier)
        .flatMap(authenticationService.generateToken)
}

object LoginUseCase {

    case class Parameters(credentials: Credentials)
}
