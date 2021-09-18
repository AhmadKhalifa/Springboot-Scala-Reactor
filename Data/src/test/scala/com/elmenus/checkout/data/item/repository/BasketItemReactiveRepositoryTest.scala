package com.elmenus.checkout.data.item.repository

import com.elmenus.checkout.data.test.utils.{DataFactory, ReactiveRepositoryTestSuite}
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mock
import org.mockito.Mockito.when
import reactor.test.StepVerifier

class BasketItemReactiveRepositoryTest extends ReactiveRepositoryTestSuite[BasketItemReactiveRepository] {

    @Mock
    var basketItemRepository: BasketItemRepository = _

    @BeforeEach
    def injectMocks(): Unit = reactiveRepository = new BasketItemReactiveRepository(basketItemRepository) {{
        transactionTemplate = BasketItemReactiveRepositoryTest.this.transactionTemplate
        jdbcScheduler = BasketItemReactiveRepositoryTest.this.jdbcScheduler
    }}

    // getAllBasketItemsForUser

    @Test
    def `test getAllBasketItemsForUser. Given an existing user, when related items queries from db, then it should return`(): Unit = {
        val user = DataFactory.generateUser()
        val basketItems = DataFactory.generateBasketItem() ::
            DataFactory.generateBasketItem() ::
            DataFactory.generateBasketItem() :: Nil

        when(basketItemRepository.findByUser(user)).thenReturn(basketItems)

        StepVerifier
            .create(reactiveRepository.findByUser(user))
            .expectNext(basketItems)
            .verifyComplete()
    }
}
