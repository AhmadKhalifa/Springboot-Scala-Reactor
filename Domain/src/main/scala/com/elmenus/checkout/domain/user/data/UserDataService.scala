package com.elmenus.checkout.domain.user.data

import com.elmenus.checkout.domain.base.BaseDataService
import com.elmenus.checkout.domain.user.model.{Credentials, User}
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
trait UserDataService extends BaseDataService {

    def getById(id: Long): Mono[User]

    def getByIdentifier(identifier: String): Mono[User]

    def userExists(identifier: String): Mono[Boolean]

    def validateCredentials(credentials: Credentials): Mono[Boolean]
}
