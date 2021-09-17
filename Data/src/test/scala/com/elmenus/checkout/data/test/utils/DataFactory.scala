package com.elmenus.checkout.data.test.utils

import com.elmenus.checkout.domain.authentication.model.{JwtAuthentication, Token}
import com.elmenus.checkout.domain.user.model.{Credentials, User, UserRole}

import java.util.{Date, UUID}
import scala.util.Random

object DataFactory {

    private val ONE_DAY = 24 * 60 * 60 * 1000

    val random: Random = new Random()

    def generateLong: Long = random.nextLong()

    def generateString: String = UUID.randomUUID().toString

    def generateToken: Token = new Token(generateString, new Date())

    def generateCredentials(username: String = generateString, password: String = generateString): Credentials =
        new Credentials(username, password)

    def generateUser(identifier: String = generateString,
                     pass: String = generateString,
                     userId: Long = generateLong): User = new User() {{
        username = identifier
        password = pass
        id = userId
        role = UserRole.CUSTOMER
    }}

    def generateJwtAuthentication: JwtAuthentication =
        JwtAuthentication(generateLong, generateString, UserRole.CUSTOMER)

    def generateDate(plusDays: Int = 0): Date = new Date(new Date().getTime + (plusDays * ONE_DAY))
}
