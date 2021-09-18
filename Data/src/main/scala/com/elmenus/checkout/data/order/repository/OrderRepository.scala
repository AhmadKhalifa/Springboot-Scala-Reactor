package com.elmenus.checkout.data.order.repository

import com.elmenus.checkout.domain.order.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait OrderRepository extends JpaRepository[Order, java.lang.Long]
