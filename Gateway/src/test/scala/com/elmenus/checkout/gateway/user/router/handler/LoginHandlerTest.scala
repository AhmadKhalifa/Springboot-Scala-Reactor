package com.elmenus.checkout.gateway.user.router.handler

import com.elmenus.checkout.common.exception.badrequest.IncorrectPasswordException
import com.elmenus.checkout.common.exception.notfound.UserNotFoundException
import com.elmenus.checkout.domain.user.usecase.LoginUseCase
import com.elmenus.checkout.gateway.user.mapper.{CredentialsMapper, TokenMapper}
import com.elmenus.checkout.gateway.utils.{DataFactory, HandlerFunctionTestSuite}
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.{doNothing, mock, when}
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.validation.Validator
import reactor.test.StepVerifier

class LoginHandlerTest extends HandlerFunctionTestSuite[LoginHandler] {

    @Mock
    var loginUseCase: LoginUseCase = _

    @Mock
    var credentialsMapper: CredentialsMapper= _

    @Mock
    var tokenMapper: TokenMapper = _

    @BeforeEach
    def injectMocks(): Unit = {
        handler = new LoginHandler(
            loginUseCase,
            credentialsMapper,
            tokenMapper
        )
        val defaultValidator = mock(classOf[Validator])
        doNothing().when(defaultValidator).validate(any(), any())
        ReflectionTestUtils.setField(handler, "defaultValidator", defaultValidator)
    }

    @Test
    def `Given valid credentialsDTO, when handler processes request, then a tokenDTO is returned`(): Unit = {
        val credentialsDTO = DataFactory.generateCredentialsDto()
        val credentials = DataFactory.generateCredentials()
        val token = DataFactory.generateToken
        val tokenDTO = DataFactory.generateTokenDto

        val request = requestOf(credentialsDTO)

        when(credentialsMapper.fromDto(credentialsDTO)).thenReturn(credentials)
        when(loginUseCase.build(any(classOf[LoginUseCase.Parameters]))).thenReturnMono(token)
        when(tokenMapper.toDto(token)).thenReturn(tokenDTO)

        StepVerifier
            .create(handler.buildPublisher(request))
            .expectNext(tokenDTO)
            .verifyComplete()
    }

    @Test
    def `Given invalid credentialsDTO (incorrect password), when handler processes request, then an IncorrectPasswordException is thrown`(): Unit = {
        val credentialsDTO = DataFactory.generateCredentialsDto()
        val credentials = DataFactory.generateCredentials()
        val exception = new IncorrectPasswordException()

        val request = requestOf(credentialsDTO)

        when(credentialsMapper.fromDto(credentialsDTO)).thenReturn(credentials)
        when(loginUseCase.build(any(classOf[LoginUseCase.Parameters]))).thenReturnErrorMono(exception)

        StepVerifier
            .create(handler.buildPublisher(request))
            .verifyErrorMatches(exception.equals(_))
    }

    @Test
    def `Given invalid credentialsDTO (non-existing user), when handler processes request, then a UserNotFoundException is thrown`(): Unit = {
        val credentialsDTO = DataFactory.generateCredentialsDto()
        val credentials = DataFactory.generateCredentials()
        val exception = new UserNotFoundException(credentialsDTO.username)

        val request = requestOf(credentialsDTO)

        when(credentialsMapper.fromDto(credentialsDTO)).thenReturn(credentials)
        when(loginUseCase.build(any(classOf[LoginUseCase.Parameters]))).thenReturnErrorMono(exception)

        StepVerifier
            .create(handler.buildPublisher(request))
            .verifyErrorMatches(exception.equals(_))
    }
}
