package com.elmenus.checkout.data.order.repository

import com.elmenus.checkout.data.base.ReactiveJpaRepository
import com.elmenus.checkout.domain.order.model.Order
import org.springframework.stereotype.Component

@Component
class OrderReactiveRepository(orderRepository: OrderRepository) extends ReactiveJpaRepository[Order, OrderRepository] {

    override protected def repository: OrderRepository = orderRepository
}
