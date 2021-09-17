package com.elmenus.checkout.domain.user.validator

import com.elmenus.checkout.common.exception.base.BusinessException
import com.elmenus.checkout.common.exception.notfound.UserNotFoundException
import com.elmenus.checkout.domain.base.BaseValidator
import com.elmenus.checkout.domain.user.data.UserDataService
import com.elmenus.checkout.domain.user.model.Credentials
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class UserExistenceValidator(userDataService: UserDataService) extends BaseValidator[Credentials] {

    override def isValid(data: Credentials): Mono[Boolean] = Mono
        .just(data)
        .map(_.username)
        .flatMap(userDataService.userExists)

    override def getValidationErrorException(data: Credentials): BusinessException =
        new UserNotFoundException(data.username)
}
