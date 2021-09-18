package com.elmenus.checkout.domain.order.validator

import com.elmenus.checkout.domain.base.BaseValidator
import com.elmenus.checkout.domain.item.data.BasketItemDataService
import com.elmenus.checkout.domain.item.model.BasketItem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
abstract class OrderAmountValidator extends BaseValidator[List[BasketItem]] {

    @Autowired
    var basketItemDataService: BasketItemDataService = _

    def isValidAmount(amount: Double): Boolean

    override def isValid(data: List[BasketItem]): Mono[Boolean] = Mono
        .just(data)
        .flatMap(basketItemDataService.calculateSubtotal)
        .map(isValidAmount)
}
