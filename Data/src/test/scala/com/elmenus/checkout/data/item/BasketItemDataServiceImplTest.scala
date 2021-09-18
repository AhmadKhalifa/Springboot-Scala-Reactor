package com.elmenus.checkout.data.item

import com.elmenus.checkout.data.item.repository.BasketItemReactiveRepository
import com.elmenus.checkout.data.test.utils.{DataFactory, DataServiceTestSuite}
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mock
import org.mockito.Mockito.when
import reactor.test.StepVerifier

class BasketItemDataServiceImplTest extends DataServiceTestSuite[BasketItemDataServiceImpl] {

    @Mock
    var basketItemReactiveRepository: BasketItemReactiveRepository = _

    @BeforeEach
    def injectMocks(): Unit = dataService = new BasketItemDataServiceImpl(basketItemReactiveRepository)

    // getAllBasketItemsForUser

    @Test
    def `test getAllBasketItemsForUser. Given an existing user, when related items queries from db, then it should return`(): Unit = {
        val user = DataFactory.generateUser()
        val basketItems = DataFactory.generateBasketItem() ::
            DataFactory.generateBasketItem() ::
            DataFactory.generateBasketItem() :: Nil

        when(basketItemReactiveRepository.findByUser(user)).thenReturnMono(basketItems)

        StepVerifier
            .create(dataService.getAllBasketItemsForUser(user))
            .expectNext(basketItems)
            .verifyComplete()
    }
}
