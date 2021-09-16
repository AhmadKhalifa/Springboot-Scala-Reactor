package com.elmenus.checkout.gateway.business.user.router.handler

import com.elmenus.checkout.domain.user.usecase.LoginUseCase
import com.elmenus.checkout.gateway.base.BaseHandlerFunction
import com.elmenus.checkout.gateway.business.user.dto.{CredentialsDTO, TokenDTO}
import com.elmenus.checkout.gateway.business.user.mapper.{CredentialsMapper, TokenMapper}
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono

@Component
@Scope("prototype")
class LoginHandler(loginUseCase: LoginUseCase,
                   credentialsMapper: CredentialsMapper,
                   tokenMapper: TokenMapper) extends BaseHandlerFunction[TokenDTO] {

    override protected def responseClass: Class[TokenDTO] = classOf[TokenDTO]

    override protected def responseClassName: String = TokenDTO.getClass.getName

    override protected def buildPublisher(request: ServerRequest): Mono[TokenDTO] =
        requestBody(classOf[CredentialsDTO])
            .map(credentialsMapper.fromDto)
            .map(LoginUseCase.Parameters(_))
            .flatMap(loginUseCase.build)
            .map(tokenMapper.toDto)
}
