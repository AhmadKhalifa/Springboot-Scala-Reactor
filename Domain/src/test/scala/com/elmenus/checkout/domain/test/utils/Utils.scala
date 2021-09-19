package com.elmenus.checkout.domain.test.utils

import com.elmenus.checkout.domain.item.model.BasketItem

import scala.jdk.CollectionConverters.ListHasAsScala

object Utils {

    def getTotalAmount(list: java.util.List[BasketItem]): Double = list
        .asScala
        .map(basketItem => basketItem.quantity * basketItem.item.price)
        .sum
}
