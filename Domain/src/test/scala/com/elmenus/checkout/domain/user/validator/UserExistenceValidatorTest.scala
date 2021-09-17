package com.elmenus.checkout.domain.user.validator

import com.elmenus.checkout.common.exception.notfound.UserNotFoundException
import com.elmenus.checkout.domain.test.utils.{DataFactory, ValidatorTestSuite}
import com.elmenus.checkout.domain.user.data.UserDataService
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mock
import org.mockito.Mockito.when
import reactor.test.StepVerifier

class UserExistenceValidatorTest extends ValidatorTestSuite[UserExistenceValidator] {

    @Mock
    var userDataService: UserDataService = _

    @BeforeEach
    def injectMocks(): Unit = validator = new UserExistenceValidator(userDataService)

    @Test
    def `Given valid credentials combination, when validator validates it, then it should pass the validation`(): Unit = {
        val userExists = true
        val credentials = DataFactory.generateCredentials

        when(userDataService.userExists(credentials.username)).thenReturnMono(userExists)

        StepVerifier
            .create(validator.validate(credentials))
            .expectNext(credentials)
            .verifyComplete()
    }

    @Test
    def `Given invalid credentials combination (non-existing user), when validator validates it, then an UserNotFoundException is thrown`(): Unit = {
        val userExists = false
        val credentials = DataFactory.generateCredentials
        val exceptionClass = classOf[UserNotFoundException]

        when(userDataService.userExists(credentials.username)).thenReturnMono(userExists)

        StepVerifier
            .create(validator.validate(credentials))
            .verifyError(exceptionClass)
    }
}
