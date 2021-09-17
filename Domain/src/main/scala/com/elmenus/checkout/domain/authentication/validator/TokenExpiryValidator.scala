package com.elmenus.checkout.domain.authentication.validator

import com.elmenus.checkout.common.exception.autorization.ExpiredTokenException
import com.elmenus.checkout.common.exception.base.BusinessException
import com.elmenus.checkout.domain.authentication.data.AuthenticationService
import com.elmenus.checkout.domain.base.BaseValidator
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

import java.util.Date

@Component
class TokenExpiryValidator(authenticationService: AuthenticationService) extends BaseValidator[String] {

    override def isValid(data: String): Mono[Boolean] = Mono
        .just(data)
        .flatMap(authenticationService.getExpirationDateFromToken)
        .zipWith(
            Mono.defer(() => Mono.just(new Date())),
            (expirationDate: Date, currentDate: Date) => expirationDate.after(currentDate)
        )

    override def getValidationErrorException(data: String): BusinessException = new ExpiredTokenException()
}
