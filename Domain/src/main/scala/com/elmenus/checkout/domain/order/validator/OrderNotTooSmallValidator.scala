package com.elmenus.checkout.domain.order.validator

import com.elmenus.checkout.common.exception.badrequest.OrderTooSmallException
import com.elmenus.checkout.common.exception.base.BusinessException
import com.elmenus.checkout.domain.item.model.BasketItem
import com.elmenus.checkout.domain.order.properties.OrderProperties
import org.springframework.stereotype.Component

@Component
class OrderNotTooSmallValidator(orderProperties: OrderProperties) extends OrderAmountValidator {

    override def isValidAmount(amount: Double): Boolean = amount >= orderProperties.minAmount

    override def getValidationErrorException(data: List[BasketItem]): BusinessException = new OrderTooSmallException()
}
