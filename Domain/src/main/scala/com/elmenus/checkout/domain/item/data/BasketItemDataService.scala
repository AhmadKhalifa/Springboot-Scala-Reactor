package com.elmenus.checkout.domain.item.data

import com.elmenus.checkout.domain.base.BaseDataService
import com.elmenus.checkout.domain.item.model.BasketItem
import com.elmenus.checkout.domain.user.model.User
import org.springframework.stereotype.Component
import reactor.core.publisher.{Flux, Mono}

import scala.jdk.CollectionConverters.IterableHasAsJava

@Component
trait BasketItemDataService extends BaseDataService {

    def calculateSubtotal(basketItems: List[BasketItem]): Mono[Double] = Flux
        .fromIterable(basketItems.asJava)
        .map(basketItem => basketItem.quantity * basketItem.item.price)
        .reduce(0.0, (t: Double, u: Double) => t + u)

    def getAllBasketItemsForUser(user: User): Mono[List[BasketItem]]
}
