package com.elmenus.checkout.domain.authentication.validator

import com.elmenus.checkout.common.exception.autorization.MissingTokenException
import com.elmenus.checkout.common.exception.base.BusinessException
import com.elmenus.checkout.domain.authentication.data.AuthenticationService
import com.elmenus.checkout.domain.base.BaseValidator
import com.elmenus.checkout.domain.user.data.UserDataService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class TokenUserValidator(authenticationDataService: AuthenticationService, userDataService: UserDataService)
    extends BaseValidator[String] {

    override def isValid(data: String): Mono[Boolean] = Mono
        .just(data)
        .flatMap(authenticationDataService.getUsernameFromToken)
        .defaultIfEmpty("")
        .flatMap(userDataService.userExists)

    override def getValidationErrorException(data: String): BusinessException = new MissingTokenException()
}
