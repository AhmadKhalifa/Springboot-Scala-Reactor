package com.elmenus.checkout.gateway.user.mapper

import com.elmenus.checkout.domain.user.model.Credentials
import com.elmenus.checkout.gateway.user.dto.CredentialsDTO
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CredentialsMapper(passwordEncoder: PasswordEncoder) {

    def fromDto(credentialsDto: CredentialsDTO): Credentials =
        new Credentials(credentialsDto.username, passwordEncoder.encode(credentialsDto.password))
}
