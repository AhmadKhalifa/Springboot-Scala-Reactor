package com.elmenus.checkout.domain.authentication.validator

import com.elmenus.checkout.common.exception.autorization.MissingTokenException
import com.elmenus.checkout.domain.test.utils.{DataFactory, ValidatorTestSuite}
import org.junit.jupiter.api.{BeforeEach, Test}
import reactor.test.StepVerifier

class TokenExistenceValidatorTest extends ValidatorTestSuite[TokenExistenceValidator] {

    @BeforeEach
    def injectMocks(): Unit = validator = new TokenExistenceValidator()

    @Test
    def `Given non-empty token, when validator validates it, then it should pass the validation`(): Unit = {
        val token = DataFactory.generateToken.token

        StepVerifier
            .create(validator.validate(token))
            .expectNext(token)
            .verifyComplete()
    }

    @Test
    def `Given empty token, when validator validates it, then a MissingTokenException is thrown`(): Unit = {
        val token = ""

        StepVerifier
            .create(validator.validate(token))
            .verifyError(classOf[MissingTokenException])
    }
}
