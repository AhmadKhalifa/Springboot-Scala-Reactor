package com.elmenus.checkout.domain.order.validator

import com.elmenus.checkout.common.exception.badrequest.OrderTooLargeException
import com.elmenus.checkout.common.exception.base.BusinessException
import com.elmenus.checkout.domain.item.model.BasketItem
import com.elmenus.checkout.domain.order.properties.OrderProperties
import org.springframework.stereotype.Component

@Component
class OrderNotTooLargeValidator(orderProperties: OrderProperties) extends OrderAmountValidator {

    override def isValidAmount(amount: Double): Boolean = amount <= orderProperties.maxAmount

    override def getValidationErrorException(data: java.util.List[BasketItem]): BusinessException = new OrderTooLargeException()
}
