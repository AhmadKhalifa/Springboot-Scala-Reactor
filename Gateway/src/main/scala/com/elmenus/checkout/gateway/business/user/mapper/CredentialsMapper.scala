package com.elmenus.checkout.gateway.business.user.mapper

import com.elmenus.checkout.domain.user.model.Credentials
import com.elmenus.checkout.gateway.business.user.dto.CredentialsDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CredentialsMapper {

    @Autowired
    var passwordEncoder: PasswordEncoder = _

    def fromDto(credentialsDto: CredentialsDTO): Credentials =
        new Credentials(credentialsDto.username, passwordEncoder.encode(credentialsDto.password))
}
