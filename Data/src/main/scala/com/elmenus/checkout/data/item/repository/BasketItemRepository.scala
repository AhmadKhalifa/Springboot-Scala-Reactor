package com.elmenus.checkout.data.item.repository

import com.elmenus.checkout.domain.item.model.BasketItem
import com.elmenus.checkout.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait BasketItemRepository extends JpaRepository[BasketItem, java.lang.Long] {

    def findByUser(user: User): java.util.List[BasketItem]
}
