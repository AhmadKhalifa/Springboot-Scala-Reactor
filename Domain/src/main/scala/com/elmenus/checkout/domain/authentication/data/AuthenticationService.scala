package com.elmenus.checkout.domain.authentication.data

import com.elmenus.checkout.domain.authentication.model.{JwtAuthentication, Token}
import com.elmenus.checkout.domain.base.BaseDataService
import com.elmenus.checkout.domain.user.model.{User, UserRole}
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

import java.util.Date

@Component
abstract class AuthenticationService {

    def getCurrentAuthentication: Mono[JwtAuthentication]

    def authenticate(token: String): Mono[JwtAuthentication]

    def getUsernameFromToken(token: String): Mono[String]

    def getRoleFromToken(token: String): Mono[UserRole.Value]

    def getUserIdFromToken(token: String): Mono[Long]

    def getExpirationDateFromToken(token: String): Mono[Date]

    def getClaimFromToken[T](token: String, claimKey: String, clazz: Class[T]): Mono[T]

    def generateToken(user: User): Mono[Token]
}
