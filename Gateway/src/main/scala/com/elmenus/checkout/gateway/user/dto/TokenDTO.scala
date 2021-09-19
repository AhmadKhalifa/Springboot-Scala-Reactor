package com.elmenus.checkout.gateway.user.dto

import java.util.Date
import scala.beans.BeanProperty

case class TokenDTO(@BeanProperty token: String,
                    @BeanProperty expirationDate: Date,
                    @BeanProperty `type`: String = "Bearer") {
    def getFullToken: String = s"${`type`} $token"
}
