package com.elmenus.checkout.domain.order.validator

import com.elmenus.checkout.common.exception.badrequest.ItemNotAvailableException
import com.elmenus.checkout.domain.test.utils.{DataFactory, ValidatorTestSuite}
import org.junit.jupiter.api.{BeforeEach, Test}
import reactor.test.StepVerifier

class OrderItemsAvailableValidatorTest extends ValidatorTestSuite[OrderItemsAvailableValidator] {

    @BeforeEach
    def injectMocks(): Unit = validator = new OrderItemsAvailableValidator()

    @Test
    def `Given a basket with its all items are available, when validator validates it, then it should pass the validation`(): Unit = {
        val basketItems = DataFactory.generateBasketItem() ::
            DataFactory.generateBasketItem() ::
            DataFactory.generateBasketItem() :: Nil

        StepVerifier
            .create(validator.validate(basketItems))
            .expectNext(basketItems)
            .verifyComplete()
    }

    @Test
    def `Given a basket with its some items are not available, when validator validates it, then a ItemNotAvailableException is thrown`(): Unit = {
        val basketItems = DataFactory.generateBasketItem() ::
            DataFactory.generateBasketItem(item = DataFactory.generateItem(available = false)) ::
            DataFactory.generateBasketItem() :: Nil
        val exceptionClass = classOf[ItemNotAvailableException]

        StepVerifier
            .create(validator.validate(basketItems))
            .verifyError(exceptionClass)

    }
}
