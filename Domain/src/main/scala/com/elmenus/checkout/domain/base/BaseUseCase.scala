package com.elmenus.checkout.domain.base

import com.elmenus.checkout.common.exception.badrequest.InvalidParameterException
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono

trait BaseUseCase[E, T] {

    def build(params: T): Publisher[E]

    def nonEmpty(value: T, exception: Exception = new InvalidParameterException()): Mono[T] = Mono
        .justOrEmpty(value)
        .deferErrorIfEmpty(exception)

    implicit class MonoHasDeferErrorIfEmpty[A](mono: Mono[A]) {

        def deferErrorIfEmpty(exception: Exception): Mono[A] = mono
            .switchIfEmpty(Mono.defer(() => Mono.error(exception)))
    }

    protected implicit class MonoHasValidate[A](mono: Mono[A]) {

        // It should be done using Mono.zip() and a reducer function for the resulted array but a complication error
        // appears so I used this bad workaround
        def validate[V <: BaseValidator[A]](validators: V*): Mono[A] = mono
            .flatMap(value => validators
                .map(_.validate(value))
                .reduce((firstMono, secondMono) => Mono.zip(firstMono, secondMono).map(_.getT1))
            )
    }
}
