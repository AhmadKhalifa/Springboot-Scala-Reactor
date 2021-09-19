package com.elmenus.checkout.domain.item.data

import com.elmenus.checkout.domain.base.BaseDataService
import com.elmenus.checkout.domain.item.model.BasketItem
import com.elmenus.checkout.domain.user.model.User
import org.springframework.stereotype.Component
import reactor.core.publisher.{Flux, Mono}

@Component
trait BasketItemDataService extends BaseDataService {

    def calculateSubtotal(basketItems: java.util.List[BasketItem]): Mono[Double] = Flux
        .fromIterable(basketItems)
        .map(basketItem => basketItem.quantity * basketItem.item.price)
        .reduce(0.0, (t: Double, u: Double) => t + u)

    def getAllBasketItemsForUser(user: User): Mono[java.util.List[BasketItem]]
}
