package com.elmenus.checkout.data.item.repository

import com.elmenus.checkout.domain.item.model.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait ItemRepository extends JpaRepository[Item, java.lang.Long]
