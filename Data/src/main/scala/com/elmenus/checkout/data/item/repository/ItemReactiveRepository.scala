package com.elmenus.checkout.data.item.repository

import com.elmenus.checkout.data.base.ReactiveJpaRepository
import com.elmenus.checkout.domain.item.model.Item
import org.springframework.stereotype.Component

@Component
class ItemReactiveRepository(itemRepository: ItemRepository) extends ReactiveJpaRepository[Item, ItemRepository] {

    override protected def repository: ItemRepository = itemRepository
}
