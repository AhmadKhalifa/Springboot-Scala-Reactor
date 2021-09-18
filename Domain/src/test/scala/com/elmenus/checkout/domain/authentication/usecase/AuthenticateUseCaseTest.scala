package com.elmenus.checkout.domain.authentication.usecase

import com.elmenus.checkout.common.exception.autorization.{ExpiredTokenException, InvalidTokenException, MissingTokenException}
import com.elmenus.checkout.common.exception.notfound.UserNotFoundException
import com.elmenus.checkout.domain.authentication.data.AuthenticationService
import com.elmenus.checkout.domain.authentication.validator.{TokenExistenceValidator, TokenNotExpiredValidator, TokenUserValidator}
import com.elmenus.checkout.domain.test.utils.{DataFactory, UseCaseTestSuite}
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mock
import org.mockito.Mockito.when
import reactor.test.StepVerifier

class AuthenticateUseCaseTest extends UseCaseTestSuite[AuthenticateUseCase] {

    @Mock
    var authenticationService: AuthenticationService = _

    @Mock
    var tokenExistenceValidator: TokenExistenceValidator = _

    @Mock
    var tokenNotExpiredValidator: TokenNotExpiredValidator = _

    @Mock
    var tokenUserValidator: TokenUserValidator = _

    @BeforeEach
    def injectMocks(): Unit = useCase = new AuthenticateUseCase(
        authenticationService,
        tokenExistenceValidator,
        tokenNotExpiredValidator,
        tokenUserValidator
    )

    @Test
    def `Given valid token, when validated, then a jwt authentication is returned`(): Unit = {
        val token = DataFactory.generateToken.token
        val jwtAuthentication = DataFactory.generateJwtAuthentication

        when(tokenExistenceValidator.validate(token)).thenReturnMono(token)
        when(tokenNotExpiredValidator.validate(token)).thenReturnMono(token)
        when(tokenUserValidator.validate(token)).thenReturnMono(token)
        when(authenticationService.authenticate(token)).thenReturnMono(jwtAuthentication)

        StepVerifier
            .create(useCase.build(AuthenticateUseCase.Parameters(token)))
            .expectNext(jwtAuthentication)
            .verifyComplete()
    }

    @Test
    def `Given missing or empty token, when validated, then a MissingTokenException is thrown`(): Unit = {
        val token = DataFactory.generateToken.token
        val exception = new MissingTokenException()

        when(tokenExistenceValidator.validate(token)).thenReturnErrorMono(exception)
        when(tokenNotExpiredValidator.validate(token)).thenReturnMono(token)
        when(tokenUserValidator.validate(token)).thenReturnMono(token)

        StepVerifier
            .create(useCase.build(AuthenticateUseCase.Parameters(token)))
            .verifyErrorMatches(_ equals exception)
    }

    @Test
    def `Given expired token, when validated, then an ExpiredTokenException is thrown`(): Unit = {
        val token = DataFactory.generateToken.token
        val exception = new ExpiredTokenException()

        when(tokenExistenceValidator.validate(token)).thenReturnMono(token)
        when(tokenNotExpiredValidator.validate(token)).thenReturnErrorMono(exception)
        when(tokenUserValidator.validate(token)).thenReturnMono(token)

        StepVerifier
            .create(useCase.build(AuthenticateUseCase.Parameters(token)))
            .verifyErrorMatches(_ equals exception)
    }

    @Test
    def `Given invalid token (malformed), when validated, then an InvalidTokenException is thrown`(): Unit = {
        val token = DataFactory.generateToken.token
        val exception = new InvalidTokenException()

        when(tokenExistenceValidator.validate(token)).thenReturnMono(token)
        when(tokenNotExpiredValidator.validate(token)).thenReturnErrorMono(exception)
        when(tokenUserValidator.validate(token)).thenReturnMono(token)

        StepVerifier
            .create(useCase.build(AuthenticateUseCase.Parameters(token)))
            .verifyErrorMatches(_ equals exception)
    }

    @Test
    def `Given invalid token (non-existing user), when validated, then an InvalidTokenException is thrown`(): Unit = {
        val token = DataFactory.generateToken.token
        val username = DataFactory.generateUser.username
        val exception = new InvalidTokenException(rootException = new UserNotFoundException(username))

        when(tokenExistenceValidator.validate(token)).thenReturnMono(token)
        when(tokenNotExpiredValidator.validate(token)).thenReturnMono(token)
        when(tokenUserValidator.validate(token)).thenReturnErrorMono(exception)

        StepVerifier
            .create(useCase.build(AuthenticateUseCase.Parameters(token)))
            .verifyErrorMatches(_ equals exception)
    }
}
