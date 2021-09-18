package com.elmenus.checkout.gateway.user.mapper

import com.elmenus.checkout.gateway.utils.{DataFactory, MapperTestSuite}
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.{BeforeEach, Test}

class TokenMapperTest extends MapperTestSuite[TokenMapper] {

    @BeforeEach
    def injectMocks(): Unit = mapper = new TokenMapper()

    @Test
    def `Given token model object, when mapped using mapper, then a dto credentialsDTO is returned`(): Unit = {
        val token = DataFactory.generateToken

        val mappedToken = mapper.toDto(token)

        assertThat(mappedToken.token).isEqualTo(token.token)
        assertThat(mappedToken.expirationDate).isEqualTo(token.expirationDate)
        assertThat(mappedToken.`type`).isEqualTo(token.`type`)
    }
}

