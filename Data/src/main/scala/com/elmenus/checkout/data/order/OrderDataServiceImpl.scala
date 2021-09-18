package com.elmenus.checkout.data.order

import com.elmenus.checkout.data.order.repository.OrderReactiveRepository
import com.elmenus.checkout.domain.order.data.OrderDataService
import com.elmenus.checkout.domain.order.model.Order
import com.elmenus.checkout.domain.payment.model.Payment
import com.elmenus.checkout.domain.user.model.User
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class OrderDataServiceImpl(orderReactiveRepository: OrderReactiveRepository) extends OrderDataService {

    override def submitOrder(user: User, payment: Payment): Mono[Order] = Mono
        .just(new Order(user, payment))
        .flatMap(orderReactiveRepository.save)
}
