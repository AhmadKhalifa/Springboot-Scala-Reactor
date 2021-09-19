package com.elmenus.checkout.domain.order.validator

import com.elmenus.checkout.common.exception.badrequest.ItemNotAvailableException
import com.elmenus.checkout.common.exception.base.BusinessException
import com.elmenus.checkout.domain.base.BaseValidator
import com.elmenus.checkout.domain.item.model.BasketItem
import org.springframework.stereotype.Component
import reactor.core.publisher.{Flux, Mono}

@Component
class OrderItemsAvailableValidator extends BaseValidator[java.util.List[BasketItem]] {

    override def isValid(data: java.util.List[BasketItem]): Mono[Boolean] = Flux
        .fromIterable(data)
        .map(_.item)
        .filter(item => !item.available)
        .count()
        .map(_ == 0)

    override def getValidationErrorException(data: java.util.List[BasketItem]): BusinessException =
        new ItemNotAvailableException()
}
