package com.elmenus.checkout.gateway.base

import com.elmenus.checkout.gateway.exception.ValidationException
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.validation.{BeanPropertyBindingResult, Validator}
import org.springframework.web.reactive.function.server.ServerResponse.BodyBuilder
import org.springframework.web.reactive.function.server.{HandlerFunction, ServerRequest, ServerResponse}
import reactor.core.publisher.Mono

@Component
@Scope("prototype")
abstract class BaseHandlerFunction[T] extends HandlerFunction[ServerResponse] {

    @Autowired
    protected var defaultValidator: Validator = _

    protected var request: ServerRequest = _

    protected def responseClass: Class[T]

    protected def responseClassName: String

    def buildPublisher(request: ServerRequest): Publisher[T]

    final override def handle(request: ServerRequest): Mono[ServerResponse] = {
        this.request = request
        handleRequest(request)
    }

    protected def handleRequest(request: ServerRequest): Mono[ServerResponse] = response(buildPublisher(request))

    protected def response(publisher: Publisher[_],
                           bodyBuilder: BodyBuilder = ServerResponse.ok(),
                           contentType: MediaType = MediaType.APPLICATION_JSON): Mono[ServerResponse] = bodyBuilder
        .contentType(contentType)
        .body(publisher, responseClass)

    protected def pathVariable(key: String): String = request.pathVariable(key)

    protected final def requestBody[S](clazz: Class[S], validator: Validator = defaultValidator): Mono[S] = request
        .bodyToMono(clazz)
        .flatMap(body => {
            val bindingResults = new BeanPropertyBindingResult(body, responseClassName)
            validator.validate(body, bindingResults)
            if (bindingResults.getAllErrors.isEmpty) Mono.justOrEmpty(body)
            else Mono.error(new ValidationException(bindingResults))
        })
}
