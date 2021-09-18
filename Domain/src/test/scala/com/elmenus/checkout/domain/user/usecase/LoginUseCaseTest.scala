package com.elmenus.checkout.domain.user.usecase

import com.elmenus.checkout.common.exception.badrequest.IncorrectPasswordException
import com.elmenus.checkout.common.exception.notfound.UserNotFoundException
import com.elmenus.checkout.domain.authentication.data.AuthenticationService
import com.elmenus.checkout.domain.test.utils.{DataFactory, UseCaseTestSuite}
import com.elmenus.checkout.domain.user.data.UserDataService
import com.elmenus.checkout.domain.user.validator.{CorrectPasswordValidator, UserExistenceValidator}
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mockito.when
import org.mockito.Mock
import reactor.test.StepVerifier

class LoginUseCaseTest extends UseCaseTestSuite[LoginUseCase] {

    @Mock
    var userDataService: UserDataService = _

    @Mock
    var authenticationService: AuthenticationService = _

    @Mock
    var userExistenceValidator: UserExistenceValidator = _

    @Mock
    var correctPasswordValidator: CorrectPasswordValidator = _

    @BeforeEach
    def injectMocks(): Unit = useCase = new LoginUseCase(
        userDataService,
        authenticationService,
        userExistenceValidator,
        correctPasswordValidator
    )

    @Test
    def `Given valid credentials, when user logs in, then a token is generated`(): Unit = {
        val credentials = DataFactory.generateCredentials
        val token = DataFactory.generateToken
        val user = DataFactory.generateUser

        when(userExistenceValidator.validate(credentials)).thenReturnMono(credentials)
        when(correctPasswordValidator.validate(credentials)).thenReturnMono(credentials)
        when(userDataService.getByIdentifier(credentials.username)).thenReturnMono(user)
        when(authenticationService.generateToken(user)).thenReturnMono(token)

        StepVerifier
            .create(useCase.build(LoginUseCase.Parameters(credentials)))
            .expectNext(token)
            .verifyComplete()
    }

    @Test
    def `Given invalid credentials (incorrect password), when user logs in, then an IncorrectPasswordException is thrown`(): Unit = {
        val credentials = DataFactory.generateCredentials
        val exception = new IncorrectPasswordException()

        when(userExistenceValidator.validate(credentials)).thenReturnMono(credentials)
        when(correctPasswordValidator.validate(credentials)).thenReturnErrorMono(exception)

        StepVerifier
            .create(useCase.build(LoginUseCase.Parameters(credentials)))
            .verifyErrorMatches(_ equals exception)
    }

    @Test
    def `Given invalid credentials (non-existing user), when user logs in, then a UserNotFoundException is thrown`(): Unit = {
        val credentials = DataFactory.generateCredentials
        val exception = new UserNotFoundException(DataFactory.generateString)

        when(userExistenceValidator.validate(credentials)).thenReturnErrorMono(exception)

        StepVerifier
            .create(useCase.build(LoginUseCase.Parameters(credentials)))
            .verifyErrorMatches(_ equals exception)
    }
}
