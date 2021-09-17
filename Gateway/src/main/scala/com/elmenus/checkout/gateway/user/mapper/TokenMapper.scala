package com.elmenus.checkout.gateway.user.mapper

import com.elmenus.checkout.domain.authentication.model.Token
import com.elmenus.checkout.gateway.user.dto.TokenDTO
import org.springframework.stereotype.Component

@Component
class TokenMapper {

    def toDto(token: Token): TokenDTO = TokenDTO(token.token, token.expirationDate, token.`type`)
}
