package com.elmenus.checkout.application.test.cucumber.step

import com.elmenus.checkout.application.test.cucumber.utils.BaseStepDefinitions
import io.cucumber.java.en.{And, Given, Then, When}

class CheckoutStepDefinitions extends BaseStepDefinitions {

    @And("customer {string} is signed in with password {string}")
    def customerIsSignedIn(username: String, password: String): Unit = {

    }

    @Given("customer has the following items in basket")
    def customerHasTheFollowingItemsInBasket(): Unit = {

    }

    @When("customer checks-out the basket")
    def customerChecksOutTheBasket(): Unit = {

    }

    @Then("a payment-intent key and with total of EGP {int} are returned to the customer to fill credit card information")
    def aPaymentIntentKeyAndWithTotalOfEGPAreReturnedToTheCustomerToFillCreditCardInformation(totalAmount: Int): Unit = {

    }

    @When("customer fills the credit card information and completes payment")
    def customerFillsTheCreditCardInformationAndCompletesPayment(): Unit = {

    }

    @Then("an event is received from the payment gateway notifying that the user completed the payment process")
    def anEventIsReceivedFromThePaymentGatewayNotifyingThatTheUserCompletedThePaymentProcess(): Unit = {

    }

    @And("payment info for the user is updated in database")
    def paymentInfoForTheUserIsUpdatedInDatabase(): Unit = {

    }

    @And("order is submitted successfully")
    def orderIsSubmittedSuccessfully(): Unit = {

    }
}
