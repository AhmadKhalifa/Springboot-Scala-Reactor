package com.elmenus.checkout.domain.authentication.validator

import com.elmenus.checkout.common.exception.autorization.ExpiredTokenException
import com.elmenus.checkout.domain.authentication.data.AuthenticationService
import com.elmenus.checkout.domain.test.utils.{DataFactory, ValidatorTestSuite}
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mock
import org.mockito.Mockito.when
import reactor.test.StepVerifier

class TokenNotExpiredValidatorTest extends ValidatorTestSuite[TokenNotExpiredValidator] {

    @Mock
    var authenticationService: AuthenticationService = _

    @BeforeEach
    def injectMocks(): Unit = validator = new TokenNotExpiredValidator(authenticationService)

    @Test
    def `Given non-expired token, when validator validates it, then it should pass the validation`(): Unit = {
        val token = DataFactory.generateToken.token
        val expirationDate = DataFactory.generateDate(1) // A date in the future

        when(authenticationService.getExpirationDateFromToken(token)).thenReturnMono(expirationDate)

        StepVerifier
            .create(validator.validate(token))
            .expectNext(token)
            .verifyComplete()
    }

    @Test
    def `Given expired token, when validator validates it, then a ExpiredTokenException is thrown`(): Unit = {
        val token = DataFactory.generateToken.token
        val expirationDate = DataFactory.generateDate(-1) // A date in the past
        val exceptionClass = classOf[ExpiredTokenException]

        when(authenticationService.getExpirationDateFromToken(token)).thenReturnMono(expirationDate)

        StepVerifier
            .create(validator.validate(token))
            .verifyError(exceptionClass)
    }
}
