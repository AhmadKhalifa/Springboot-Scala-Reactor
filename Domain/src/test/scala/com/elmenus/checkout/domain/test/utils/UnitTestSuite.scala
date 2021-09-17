package com.elmenus.checkout.domain.test.utils

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.stubbing.OngoingStubbing
import reactor.core.publisher.Mono

@ExtendWith(Array(classOf[MockitoExtension]))
class UnitTestSuite {

    @BeforeEach
    def setup(): Unit = MockitoAnnotations.openMocks(this)

    def monoOf[T](value: T): Mono[T] = Mono.just(value)

    def errorMonoOf[T](exception: Exception): Mono[T] = Mono.error[T](exception)

    implicit class OngoingStubbingHasThenReturnMono[T](ongoingStubbing: OngoingStubbing[Mono[T]]) {

        def thenReturnMono(value: T): OngoingStubbing[Mono[T]] = ongoingStubbing.thenReturn(monoOf(value))
    }

    implicit class OngoingStubbingHasThenReturnErrorMono[T](ongoingStubbing: OngoingStubbing[Mono[T]]) {

        def thenReturnErrorMono(exception: Exception): OngoingStubbing[Mono[T]] =
            ongoingStubbing.thenReturn(errorMonoOf(exception))
    }
}
