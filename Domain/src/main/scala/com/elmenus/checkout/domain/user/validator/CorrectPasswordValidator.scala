package com.elmenus.checkout.domain.user.validator

import com.elmenus.checkout.common.exception.badrequest.IncorrectPasswordException
import com.elmenus.checkout.common.exception.base.BusinessException
import com.elmenus.checkout.domain.base.BaseValidator
import com.elmenus.checkout.domain.user.data.UserDataService
import com.elmenus.checkout.domain.user.model.Credentials
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CorrectPasswordValidator(userDataService: UserDataService) extends BaseValidator[Credentials] {

    override def isValid(data: Credentials): Mono[Boolean] = Mono
        .just(data)
        .flatMap(userDataService.validateCredentials)

    override def getValidationErrorException(data: Credentials): BusinessException = new IncorrectPasswordException()
}
