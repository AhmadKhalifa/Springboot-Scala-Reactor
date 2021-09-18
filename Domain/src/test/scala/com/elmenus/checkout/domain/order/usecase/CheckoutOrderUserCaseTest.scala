package com.elmenus.checkout.domain.order.usecase

import com.elmenus.checkout.common.exception.badrequest.{ItemNotAvailableException, OrderTooLargeException, OrderTooSmallException}
import com.elmenus.checkout.domain.item.data.BasketItemDataService
import com.elmenus.checkout.domain.order.validator.{OrderItemsAvailableValidator, OrderNotTooLargeValidator, OrderNotTooSmallValidator}
import com.elmenus.checkout.domain.payment.data.PaymentDataService
import com.elmenus.checkout.domain.payment.gateway.PaymentGateway
import com.elmenus.checkout.domain.test.utils.{DataFactory, UseCaseTestSuite, Utils}
import com.elmenus.checkout.domain.user.data.UserDataService
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mock
import org.mockito.Mockito.when
import reactor.test.StepVerifier

class CheckoutOrderUserCaseTest extends UseCaseTestSuite[CheckoutOrderUseCase] {

    @Mock
    var userDataService: UserDataService = _

    @Mock
    var basketItemDataService: BasketItemDataService = _

    @Mock
    var orderItemsAvailableValidator: OrderItemsAvailableValidator = _

    @Mock
    var orderNotTooSmallValidator: OrderNotTooSmallValidator = _

    @Mock
    var orderNotTooLargeValidator: OrderNotTooLargeValidator = _

    @Mock
    var paymentGateway: PaymentGateway = _

    @Mock
    var paymentDataService: PaymentDataService = _

    @BeforeEach
    def injectMocks(): Unit = useCase = new CheckoutOrderUseCase(
        userDataService,
        basketItemDataService,
        orderItemsAvailableValidator,
        orderNotTooSmallValidator,
        orderNotTooLargeValidator,
        paymentGateway,
        paymentDataService
    )

    @Test
    def `Given valid order, when user tries to checkout, then a payment is returned to the user`(): Unit = {
        val user = DataFactory.generateUser
        val userId = user.id
        val basketItems = DataFactory.generateBasketItem() ::
            DataFactory.generateBasketItem() ::
            DataFactory.generateBasketItem() :: Nil
        val amount = Utils.getTotalAmount(basketItems)
        val payment = DataFactory.generatePayment()

        when(userDataService.getById(userId)).thenReturnMono(user)
        when(basketItemDataService.getAllBasketItemsForUser(user)).thenReturnMono(basketItems)
        when(orderItemsAvailableValidator.validate(basketItems)).thenReturnMono(basketItems)
        when(orderNotTooSmallValidator.validate(basketItems)).thenReturnMono(basketItems)
        when(orderNotTooLargeValidator.validate(basketItems)).thenReturnMono(basketItems)
        when(basketItemDataService.calculateSubtotal(basketItems)).thenReturnMono(amount)
        when(paymentGateway.initializePayment(userId, amount)).thenReturnMono(payment)
        when(paymentDataService.save(payment)).thenReturnMono(payment)

        StepVerifier
            .create(useCase.build(CheckoutOrderUseCase.Parameters(userId)))
            .expectNext(payment)
            .verifyComplete()
    }

    @Test
    def `Given invalid order(an item is not available), when user tries to checkout, then a is thrown`(): Unit = {
        val user = DataFactory.generateUser
        val userId = user.id
        val basketItems = DataFactory.generateBasketItem() ::
            DataFactory.generateBasketItem(item = DataFactory.generateItem(available = false)) ::
            DataFactory.generateBasketItem() :: Nil
        val exception = new ItemNotAvailableException()

        when(userDataService.getById(userId)).thenReturnMono(user)
        when(basketItemDataService.getAllBasketItemsForUser(user)).thenReturnMono(basketItems)
        when(orderItemsAvailableValidator.validate(basketItems)).thenReturnErrorMono(exception)
        when(orderNotTooSmallValidator.validate(basketItems)).thenReturnMono(basketItems)
        when(orderNotTooLargeValidator.validate(basketItems)).thenReturnMono(basketItems)

        StepVerifier
            .create(useCase.build(CheckoutOrderUseCase.Parameters(userId)))
            .verifyErrorMatches(_ equals exception)
    }

    @Test
    def `Given invalid order(less than minimum), when user tries to checkout, then a is thrown`(): Unit = {
        val user = DataFactory.generateUser
        val userId = user.id
        val basketItems = DataFactory.generateBasketItem(item = DataFactory.generateItem(price = 2)) :: Nil
        val exception = new OrderTooSmallException()

        when(userDataService.getById(userId)).thenReturnMono(user)
        when(basketItemDataService.getAllBasketItemsForUser(user)).thenReturnMono(basketItems)
        when(orderItemsAvailableValidator.validate(basketItems)).thenReturnMono(basketItems)
        when(orderNotTooSmallValidator.validate(basketItems)).thenReturnErrorMono(exception)
        when(orderNotTooLargeValidator.validate(basketItems)).thenReturnMono(basketItems)

        StepVerifier
            .create(useCase.build(CheckoutOrderUseCase.Parameters(userId)))
            .verifyErrorMatches(_ equals exception)
    }

    @Test
    def `Given invalid order(more than maximum), when user tries to checkout, then a is thrown`(): Unit = {
        val user = DataFactory.generateUser
        val userId = user.id
        val basketItems = DataFactory.generateBasketItem(item = DataFactory.generateItem(price = 2)) :: Nil
        val exception = new OrderTooLargeException()

        when(userDataService.getById(userId)).thenReturnMono(user)
        when(basketItemDataService.getAllBasketItemsForUser(user)).thenReturnMono(basketItems)
        when(orderItemsAvailableValidator.validate(basketItems)).thenReturnMono(basketItems)
        when(orderNotTooSmallValidator.validate(basketItems)).thenReturnMono(basketItems)
        when(orderNotTooLargeValidator.validate(basketItems)).thenReturnErrorMono(exception)

        StepVerifier
            .create(useCase.build(CheckoutOrderUseCase.Parameters(userId)))
            .verifyErrorMatches(_ equals exception)
    }
}
