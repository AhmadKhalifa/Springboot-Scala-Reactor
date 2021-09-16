package com.elmenus.checkout.gateway.exception

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.{HttpStatus, MediaType}
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.{RequestPredicates, RouterFunction, RouterFunctions, ServerRequest, ServerResponse}
import reactor.core.publisher.Mono

@Order(Int.MinValue)
@Component
class GlobalWebFluxExceptionHandler(globalExceptionAttributes: GlobalExceptionAttributes,
                                    applicationContext: ApplicationContext,
                                    serverCodecConfigurer: ServerCodecConfigurer)
    extends AbstractErrorWebExceptionHandler(
        globalExceptionAttributes,
        new WebProperties.Resources(),
        applicationContext
    ) {
    {
        super.setMessageWriters(serverCodecConfigurer.getWriters)
        super.setMessageReaders(serverCodecConfigurer.getReaders)
    }

    override def getRoutingFunction(errorAttributes: ErrorAttributes): RouterFunction[ServerResponse] = RouterFunctions
        .route(RequestPredicates.all(), renderErrorResponse)

    private def renderErrorResponse(request: ServerRequest): Mono[ServerResponse] = {
        val errorAttributes = getErrorAttributes(request, ErrorAttributeOptions.defaults())
        val status = Option(errorAttributes.get(GlobalExceptionAttributes.KEY_STATUS))
            .filter(_.isInstanceOf[Int])
            .map(_.asInstanceOf[Int])
            .map(HttpStatus.valueOf)
            .getOrElse(HttpStatus.BAD_REQUEST)
        ServerResponse
            .status(status)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(errorAttributes))
    }
}
