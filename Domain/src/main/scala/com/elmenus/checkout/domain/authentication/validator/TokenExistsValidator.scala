package com.elmenus.checkout.domain.authentication.validator

import com.elmenus.checkout.common.exception.autorization.MissingTokenException
import com.elmenus.checkout.common.exception.base.BusinessException
import com.elmenus.checkout.domain.base.BaseValidator
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class TokenExistsValidator extends BaseValidator[String] {

    override def isValid(data: String): Mono[Boolean] = Mono
        .justOrEmpty(data)
        .defaultIfEmpty("")
        .map(_.nonEmpty)

    override def getValidationErrorException(data: String): BusinessException = new MissingTokenException()
}
