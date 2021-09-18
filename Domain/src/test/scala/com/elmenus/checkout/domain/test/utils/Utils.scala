package com.elmenus.checkout.domain.test.utils

import com.elmenus.checkout.domain.item.model.BasketItem

object Utils {

    def getTotalAmount(list: List[BasketItem]): Double = list
        .map(basketItem => basketItem.quantity * basketItem.item.price)
        .sum
}
