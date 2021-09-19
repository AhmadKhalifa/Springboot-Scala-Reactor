package com.elmenus.checkout.gateway.order

import com.elmenus.checkout.gateway.order.handler.factory.{CheckoutOrderHandlerFactory, UpdateOrderPaymentHandlerFactory}
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.reactive.function.server.RequestPredicates.POST
import org.springframework.web.reactive.function.server.{RouterFunction, RouterFunctions, ServerResponse}

@Configuration
class OrderRouterConfiguration {

    @Bean
    def orderRouter(checkoutOrderHandlerFactory: CheckoutOrderHandlerFactory,
                   updateOrderPaymentHandlerFactory: UpdateOrderPaymentHandlerFactory): RouterFunction[ServerResponse] =
        RouterFunctions
            .route(POST(OrderRouterConfiguration.Endpoints.CHECKOUT), checkoutOrderHandlerFactory)
            .andRoute(POST(OrderRouterConfiguration.Endpoints.PAYMENT_WEBHOOK), updateOrderPaymentHandlerFactory)
}

object OrderRouterConfiguration {
    object Endpoints {
        private val BASE_URL = "/orders"
        val CHECKOUT = s"$BASE_URL/checkout"
        val PAYMENT_WEBHOOK = s"$BASE_URL/payment/webhook"
    }
}