package com.elmenus.checkout.gateway.user.mapper

import com.elmenus.checkout.gateway.utils.{DataFactory, MapperTestSuite}
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mock
import org.mockito.Mockito.when
import org.springframework.security.crypto.password.PasswordEncoder

class CredentialsMapperTest extends MapperTestSuite[CredentialsMapper] {

    @Mock
    var passwordEncoder: PasswordEncoder = _

    @BeforeEach
    def injectMocks(): Unit = mapper = new CredentialsMapper(passwordEncoder)

    @Test
    def `Given credentialsDTO object, when mapped using mapper, then a domain model of credentials is returned`(): Unit = {
        val credentialsDTO = DataFactory.generateCredentialsDto()
        val password = credentialsDTO.password
        val encodedPassword = DataFactory.generateString
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword)

        val mappedCredentials = mapper.fromDto(credentialsDTO)

        assertThat(mappedCredentials.username).isEqualTo(credentialsDTO.username)
        assertThat(mappedCredentials.password).isEqualTo(encodedPassword)
    }
}
