package com.elmenus.checkout.data.item

import com.elmenus.checkout.data.item.repository.BasketItemReactiveRepository
import com.elmenus.checkout.domain.item.data.BasketItemDataService
import com.elmenus.checkout.domain.item.model.BasketItem
import com.elmenus.checkout.domain.user.model.User
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class BasketItemDataServiceImpl(basketItemReactiveRepository: BasketItemReactiveRepository)
    extends BasketItemDataService {

    override def getAllBasketItemsForUser(user: User): Mono[java.util.List[BasketItem]] = basketItemReactiveRepository
        .findByUser(user)
}
