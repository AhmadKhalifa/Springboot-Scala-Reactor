package com.elmenus.checkout.data.item.repository

import com.elmenus.checkout.data.base.ReactiveJpaRepository
import com.elmenus.checkout.domain.item.model.BasketItem
import com.elmenus.checkout.domain.user.model.User
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class BasketItemReactiveRepository(basketItemRepository: BasketItemRepository)
    extends ReactiveJpaRepository[BasketItem, BasketItemRepository] {

    override protected def repository: BasketItemRepository = basketItemRepository

    def findByUser(user: User): Mono[List[BasketItem]] = Mono
        .just(user)
        .flatMap(user => Mono.fromCallable(() => repository.findByUser(user)))
        .subscribeOn(jdbcScheduler)
}
