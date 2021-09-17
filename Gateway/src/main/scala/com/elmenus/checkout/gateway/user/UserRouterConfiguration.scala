package com.elmenus.checkout.gateway.user

import com.elmenus.checkout.gateway.user.router.handler.LoginHandlerFactory
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.reactive.function.server.RequestPredicates.POST
import org.springframework.web.reactive.function.server.{RouterFunction, RouterFunctions, ServerResponse}

@Configuration
class UserRouterConfiguration {

    object Routes {
        val BASE_URL = "/users"
        val AUTH = s"$BASE_URL/auth"
    }

    @Bean
    def userRouter(loginHandlerFactory: LoginHandlerFactory): RouterFunction[ServerResponse] = RouterFunctions
        .route(POST(Routes.AUTH), loginHandlerFactory)
}
