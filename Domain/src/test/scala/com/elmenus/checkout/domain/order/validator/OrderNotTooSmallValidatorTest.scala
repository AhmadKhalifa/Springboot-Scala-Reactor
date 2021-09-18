package com.elmenus.checkout.domain.order.validator

import com.elmenus.checkout.common.exception.badrequest.OrderTooSmallException
import com.elmenus.checkout.domain.item.data.ItemDataService
import com.elmenus.checkout.domain.order.properties.OrderProperties
import com.elmenus.checkout.domain.test.utils.{DataFactory, Utils, ValidatorTestSuite}
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mock
import org.mockito.Mockito.when
import org.springframework.test.util.ReflectionTestUtils
import reactor.test.StepVerifier

class OrderNotTooSmallValidatorTest extends ValidatorTestSuite[OrderNotTooSmallValidator] {

    private val MIN_AMOUNT = 100
    private val MAX_AMOUNT = 1500

    @Mock
    var itemDataService: ItemDataService = _

    @BeforeEach
    def injectMocks(): Unit = {
        validator = new OrderNotTooSmallValidator(new OrderProperties() {
            minAmount = MIN_AMOUNT
            maxAmount = MAX_AMOUNT
        })
        ReflectionTestUtils.setField(validator, "itemDataService", itemDataService)
    }

    @Test
    def `Given a basket with a total exceeding the min limit, when validator validates it, then it should pass the validation`(): Unit = {
        val basketItems = DataFactory.generateBasketItem(item = DataFactory.generateItem(price = 150)) ::
            DataFactory.generateBasketItem(quantity = 2, item = DataFactory.generateItem(price = 150)) ::
            DataFactory.generateBasketItem(item = DataFactory.generateItem(price = 50)) :: Nil
        val amount = Utils.getTotalAmount(basketItems)

        when(itemDataService.getCartItemsSubtotal(basketItems)).thenReturnMono(amount)

        assertThat(amount).isBetween(MIN_AMOUNT, MAX_AMOUNT)
        StepVerifier
            .create(validator.validate(basketItems))
            .expectNext(basketItems)
            .verifyComplete()
    }

    @Test
    def `Given a basket with a total not exceeding the min limit, when validator validates it, then a OrderTooSmallException is thrown`(): Unit = {
        val basketItems = DataFactory.generateBasketItem(item = DataFactory.generateItem(price = 2)) :: Nil
        val amount = Utils.getTotalAmount(basketItems)
        val exceptionClass = classOf[OrderTooSmallException]

        when(itemDataService.getCartItemsSubtotal(basketItems)).thenReturnMono(amount)

        assertThat(amount).isLessThanOrEqualTo(MIN_AMOUNT)
        StepVerifier
            .create(validator.validate(basketItems))
            .verifyError(exceptionClass)
    }
}
