package com.elmenus.checkout.application.test.utils

import com.elmenus.checkout.domain.authentication.model.{JwtAuthentication, Token}
import com.elmenus.checkout.domain.user.model.{Credentials, User, UserRole}
import com.elmenus.checkout.gateway.user.dto.{CredentialsDTO, TokenDTO}

import java.util.{Date, UUID}
import scala.util.Random

object DataFactory {

    private val ONE_DAY = 24 * 60 * 60 * 1000

    val random: Random = new Random()

    def generateLong: Long = random.nextLong()

    def generateString: String = UUID.randomUUID().toString

    def generateToken: Token = Token(generateString, new Date())

    def generateTokenDto: TokenDTO = TokenDTO(generateString, new Date())

    def generateCredentials(username: String = generateString, password: String = generateString): Credentials =
        new Credentials(username, password)

    def generateCredentialsDto(identifier: String = generateString, pass: String = generateString): CredentialsDTO =
        new CredentialsDTO() {{
            username = identifier
            password = pass
        }}

    def generateUser(identifier: String = generateString,
                     pass: String = generateString,
                     userRole: UserRole.Value = UserRole.CUSTOMER,
                     userId: Long = generateLong): User = new User() {{
        username = identifier
        password = pass
        id = userId
        role = userRole
    }}

    def generateJwtAuthentication: JwtAuthentication =
        JwtAuthentication(generateLong, generateString, UserRole.CUSTOMER)

    def generateDate(plusDays: Int = 0): Date = new Date(new Date().getTime + (plusDays * ONE_DAY))
}
