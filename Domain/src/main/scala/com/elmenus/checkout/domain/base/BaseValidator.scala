package com.elmenus.checkout.domain.base

import com.elmenus.checkout.common.exception.base.{BusinessException, TechnicalException}
import lombok.extern.slf4j.Slf4j
import reactor.core.publisher.Mono

@Slf4j
trait BaseValidator[T] {

    def isValid(data: T): Mono[Boolean]

    def getValidationErrorException(data: T): BusinessException

    def validate(data: T): Mono[T] = isValid(data)
        .defaultIfEmpty(false)
        .onErrorMap(throwable => new TechnicalException(
            s"Something went wrong during validation: ${throwable.getMessage}",
            rootException = throwable)
        )
        .flatMap(isValid => if (isValid) Mono.justOrEmpty(data) else Mono.error(getValidationErrorException(data)))
}
