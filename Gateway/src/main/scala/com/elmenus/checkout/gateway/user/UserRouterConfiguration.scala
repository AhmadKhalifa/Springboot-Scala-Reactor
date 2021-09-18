package com.elmenus.checkout.gateway.user

import com.elmenus.checkout.gateway.user.router.handler.LoginHandlerFactory
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.reactive.function.server.RequestPredicates.POST
import org.springframework.web.reactive.function.server.{RouterFunction, RouterFunctions, ServerResponse}

@Configuration
class UserRouterConfiguration {

    @Bean
    def userRouter(loginHandlerFactory: LoginHandlerFactory): RouterFunction[ServerResponse] = RouterFunctions
        .route(POST(UserRouterConfiguration.Endpoints.AUTH), loginHandlerFactory)
}

object UserRouterConfiguration {
    object Endpoints {
        private val BASE_URL = "/users"
        val AUTH = s"$BASE_URL/auth"
    }
}