package com.elmenus.checkout.data.test.utils

import com.elmenus.checkout.domain.authentication.model.{JwtAuthentication, Token}
import com.elmenus.checkout.domain.item.model.{BasketItem, Item}
import com.elmenus.checkout.domain.order.model.Order
import com.elmenus.checkout.domain.payment.model.{Payment, PaymentState}
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

    def generateInt: Int = random.nextInt()

    def generateDouble: Double = random.nextDouble()

    def generateItem(name: String = generateString, price: Double = generateDouble, available: Boolean = true): Item =
        new Item(name, price, available)

    def generateBasketItem(user: User = generateUser(), quantity: Int = 1, item: Item = generateItem()): BasketItem =
        new BasketItem(user, item, quantity)

    def generatePayment(key: String = generateString,
                        amount: Double = generateDouble,
                        state: PaymentState.Value = PaymentState.PENDING) = new Payment(key, amount, state)

    def generateOrder(user: User = generateUser(), payment: Payment = generatePayment()): Order =
        new Order(user, payment)
}
