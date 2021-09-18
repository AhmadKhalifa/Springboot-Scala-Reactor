package com.elmenus.checkout.data.order.repository

import com.elmenus.checkout.data.test.utils.{DataFactory, ReactiveRepositoryTestSuite}
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mock
import org.mockito.Mockito.when
import reactor.test.StepVerifier

class OrderReactiveRepositoryTest extends ReactiveRepositoryTestSuite[OrderReactiveRepository] {

    @Mock
    var orderRepository: OrderRepository = _

    @BeforeEach
    def injectMocks(): Unit = reactiveRepository = new OrderReactiveRepository(orderRepository){{
        transactionTemplate = OrderReactiveRepositoryTest.this.transactionTemplate
        jdbcScheduler = OrderReactiveRepositoryTest.this.jdbcScheduler
    }}

    @Test
    def `test save. Given an order, when saved in db, then it should be returned as a result`(): Unit = {
        stubTransactionTemplate()

        val order = DataFactory.generateOrder()

        when(orderRepository.save(order)).thenReturn(order)

        StepVerifier
            .create(reactiveRepository.save(order))
            .expectNext(order)
            .verifyComplete()
    }
}
