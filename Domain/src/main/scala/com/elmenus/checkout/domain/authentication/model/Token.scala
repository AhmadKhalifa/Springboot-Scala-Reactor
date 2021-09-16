package com.elmenus.checkout.domain.authentication.model

import java.util.Date

case class Token(token: String, expirationDate: Date, `type`: String = Token.AUTH_HEADER_PREFIX.trim)

object Token {
    val KEY_USER_ID = "userId"
    val KEY_ROLE = "role"
    val AUTH_HEADER_PREFIX = "Bearer "
}
