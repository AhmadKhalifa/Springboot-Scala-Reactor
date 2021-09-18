package com.elmenus.checkout.data.order

import com.elmenus.checkout.data.order.repository.OrderReactiveRepository
import com.elmenus.checkout.data.test.utils.{DataFactory, DataServiceTestSuite}
import com.elmenus.checkout.domain.order.model.Order
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.when
import reactor.test.StepVerifier

class OrderDataServiceTest extends DataServiceTestSuite[OrderDataServiceImpl] {

    @Mock
    var orderReactiveRepository: OrderReactiveRepository = _

    @BeforeEach
    def injectMocks(): Unit = dataService = new OrderDataServiceImpl(orderReactiveRepository)

    @Test
    def `test submitOrder. Given a user and payment, when method is called, then it create a new order attached to them and save it in db`(): Unit = {
        val user = DataFactory.generateUser()
        val payment = DataFactory.generatePayment()
        val order = DataFactory.generateOrder(user, payment)

        when(orderReactiveRepository.save(any(classOf[Order]))).thenReturnMono(order)

        StepVerifier
            .create(dataService.submitOrder(user, payment))
            .expectNext(order)
            .verifyComplete()
    }
}
