package com.elmenus.checkout.application.test.cucumber.step

import com.elmenus.checkout.application.test.cucumber.utils.BaseStepDefinitions
import com.elmenus.checkout.application.test.cucumber.utils.ScenarioData.Keys
import com.elmenus.checkout.application.test.utils.DataFactory
import com.elmenus.checkout.domain.item.model.{BasketItem, Item}
import com.elmenus.checkout.domain.payment.model.PaymentState
import com.elmenus.checkout.gateway.order.OrderRouterConfiguration
import com.elmenus.checkout.gateway.order.dto.{PaymentDTO, PaymentUpdatesDTO, PaymentUpdatesResponseDTO}
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.{And, Given, Then, When}
import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.awaitility.Duration

import scala.jdk.CollectionConverters.CollectionHasAsScala

class CheckoutStepDefinitions extends BaseStepDefinitions {

    @Given("customer has the following items in basket")
    def customerHasTheFollowingItemsInBasket(datatable: DataTable): Unit = {
        val user = scenarioData.currentUser
        datatable
            .asMaps()
            .asScala
            .map(row => {
                val name = row.get("Item name")
                val quantity = row.get("Quantity").toInt
                val price = row.get("Price per item").toDouble
                val available = row.get("Is item available").toBoolean
                val item = new Item(name, price, available)
                val basketItem = new BasketItem(user, item, quantity)
                (item, basketItem)
            })
            .foreach(itemTuple => {
                val item = itemRepository.save(itemTuple._1)
                val basketItem = itemTuple._2
                basketItem.item = item
                basketItemRepository.save(basketItem)
            })
    }

    @When("customer checks-out the basket")
    def customerChecksOutTheBasket(): Unit = {
        webClient.post(
            OrderRouterConfiguration.Endpoints.CHECKOUT,
            DataFactory.generateOrderCheckoutDTO(scenarioData.currentUser.id)
        )
    }

    @Then("a payment-intent key and with total of EGP {int} are returned to the customer to fill credit card information")
    def aPaymentIntentKeyAndWithTotalOfEGPAreReturnedToTheCustomerToFillCreditCardInformation(totalAmount: Int): Unit = {
        webClient
            .responseBody()
            .expectBody(classOf[PaymentDTO])
            .consumeWith(results => {
                val payment = results.getResponseBody
                assertThat(payment.amount).isEqualTo(totalAmount)
                assertThat(payment.key).isNotNull
                scenarioData.set(Keys.PAYMENT_KEY, payment.key)
            })
        await()
            .atMost(Duration.FIVE_SECONDS)
            .until(() => scenarioData.contains[String](Keys.PAYMENT_KEY))
    }

    @When("customer fills the credit card information and completes payment")
    def customerFillsTheCreditCardInformationAndCompletesPayment(): Unit = {
        webClient.post(
            OrderRouterConfiguration.Endpoints.PAYMENT_WEBHOOK,
            DataFactory.generatePaymentUpdatesDto(
                PaymentUpdatesDTO.Events.SUCCEEDED,
                scenarioData.currentUser.id,
                scenarioData.get[String](Keys.PAYMENT_KEY)
            )
        )
    }

    @Then("an event is received from the payment gateway notifying that the user completed the payment process")
    def anEventIsReceivedFromThePaymentGatewayNotifyingThatTheUserCompletedThePaymentProcess(): Unit = {
        webClient
            .responseBody()
            .expectBody(classOf[PaymentUpdatesResponseDTO])
            .consumeWith(results => assertThat(results.getResponseBody.message).isEqualTo("OK"))
    }

    @And("payment info for the user is updated in database")
    def paymentInfoForTheUserIsUpdatedInDatabase(): Unit = {
        val paymentKey = scenarioData.get[String](Keys.PAYMENT_KEY)
        assertThat(paymentKey).isNotNull
        assertThat(paymentKey).isNotEmpty
        val paymentOptional = paymentRepository.findByKey(paymentKey)
        assertThat(paymentOptional).isNotEmpty
        val payment = paymentOptional.get()
        assertThat(payment.key).isEqualTo(paymentKey)
        assertThat(payment.state.toString).isEqualTo(PaymentState.SUCCEEDED.toString)
    }

    @And("order is submitted successfully")
    def orderIsSubmittedSuccessfully(): Unit = {
        val payment = paymentRepository.findByKey(scenarioData.get[String](Keys.PAYMENT_KEY)).get()
        val orders = orderRepository.findAll()
        assertThat(orders.size()).isOne
        val order = orders.get(0)
        assertThat(order.user).isNotNull
        assertThat(order.payment).isNotNull
        assertThat(order.payment.id).isEqualTo(payment.id)
        assertThat(order.user.id).isEqualTo(scenarioData.currentUser.id)
    }
}
