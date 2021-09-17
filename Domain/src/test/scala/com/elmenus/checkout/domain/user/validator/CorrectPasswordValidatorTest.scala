package com.elmenus.checkout.domain.user.validator

import com.elmenus.checkout.common.exception.badrequest.IncorrectPasswordException
import com.elmenus.checkout.domain.test.utils.{DataFactory, ValidatorTestSuite}
import com.elmenus.checkout.domain.user.data.UserDataService
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mock
import org.mockito.Mockito.when
import reactor.test.StepVerifier

class CorrectPasswordValidatorTest extends ValidatorTestSuite[CorrectPasswordValidator] {

    @Mock
    var userDataService: UserDataService = _

    @BeforeEach
    def injectMocks(): Unit = validator = new CorrectPasswordValidator(userDataService)

    @Test
    def `Given valid credentials combination, when validator validates it, then it should pass the validation`(): Unit = {
        val isValidCredentials = true
        val credentials = DataFactory.generateCredentials

        when(userDataService.validateCredentials(credentials)).thenReturnMono(isValidCredentials)

        StepVerifier
            .create(validator.validate(credentials))
            .expectNext(credentials)
            .verifyComplete()
    }

    @Test
    def `Given invalid credentials combination (incorrect password), when validator validates it, then an IncorrectPasswordException is thrown`(): Unit = {
        val isValidCredentials = false
        val credentials = DataFactory.generateCredentials
        val exceptionClass = classOf[IncorrectPasswordException]

        when(userDataService.validateCredentials(credentials)).thenReturnMono(isValidCredentials)

        StepVerifier
            .create(validator.validate(credentials))
            .verifyError(exceptionClass)
    }
}
