package com.elmenus.checkout.domain.authentication.validator

import com.elmenus.checkout.common.exception.autorization.InvalidTokenException
import com.elmenus.checkout.domain.authentication.data.AuthenticationService
import com.elmenus.checkout.domain.test.utils.{DataFactory, ValidatorTestSuite}
import com.elmenus.checkout.domain.user.data.UserDataService
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mock
import org.mockito.Mockito.when
import reactor.test.StepVerifier

class TokenUserValidatorTest extends ValidatorTestSuite[TokenUserValidator] {

    @Mock
    var authenticationService: AuthenticationService = _

    @Mock
    var userDataService: UserDataService = _

    @BeforeEach
    def injectMocks(): Unit = validator = new TokenUserValidator(authenticationService, userDataService)

    @Test
    def `Given valid token, when validator validates it, then it should pass the validation`(): Unit = {
        val userExists = true
        val token = DataFactory.generateToken.token
        val username = DataFactory.generateUser.username

        when(authenticationService.getUsernameFromToken(token)).thenReturnMono(username)
        when(userDataService.userExists(username)).thenReturnMono(userExists)

        StepVerifier
            .create(validator.validate(token))
            .expectNext(token)
            .verifyComplete()
    }

    @Test
    def `Given invalid token (non-existing user), when validator validates it, then a MissingTokenException is thrown`(): Unit = {
        val userExists = false
        val token = DataFactory.generateToken.token
        val username = DataFactory.generateUser.username
        val exceptionClass = classOf[InvalidTokenException]

        when(authenticationService.getUsernameFromToken(token)).thenReturnMono(username)
        when(userDataService.userExists(username)).thenReturnMono(userExists)

        StepVerifier
            .create(validator.validate(token))
            .verifyError(exceptionClass)
    }
}
