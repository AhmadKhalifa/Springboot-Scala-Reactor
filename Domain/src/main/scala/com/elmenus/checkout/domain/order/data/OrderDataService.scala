package com.elmenus.checkout.domain.order.data

import com.elmenus.checkout.domain.base.BaseDataService
import com.elmenus.checkout.domain.order.model.Order
import com.elmenus.checkout.domain.payment.model.Payment
import com.elmenus.checkout.domain.user.model.User
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
trait OrderDataService extends BaseDataService {

    def submitOrder(user: User, payment: Payment): Mono[Order]
}
