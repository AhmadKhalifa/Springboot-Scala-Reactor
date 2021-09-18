package com.elmenus.checkout.domain.order.validator

import com.elmenus.checkout.common.exception.badrequest.ItemNotAvailableException
import com.elmenus.checkout.common.exception.base.BusinessException
import com.elmenus.checkout.domain.base.BaseValidator
import com.elmenus.checkout.domain.item.model.BasketItem
import org.springframework.stereotype.Component
import reactor.core.publisher.{Flux, Mono}

import scala.jdk.CollectionConverters.IterableHasAsJava

@Component
class OrderItemsAvailableValidator extends BaseValidator[List[BasketItem]] {

    override def isValid(data: List[BasketItem]): Mono[Boolean] = Flux
        .fromIterable(data.asJava)
        .map(_.item)
        .filter(item => !item.available)
        .count()
        .map(_ == 0)

    override def getValidationErrorException(data: List[BasketItem]): BusinessException =
        new ItemNotAvailableException()
}
