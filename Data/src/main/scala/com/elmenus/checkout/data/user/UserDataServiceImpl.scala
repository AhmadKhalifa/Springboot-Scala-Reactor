package com.elmenus.checkout.data.user

import com.elmenus.checkout.data.user.repository.UserReactiveRepository
import com.elmenus.checkout.domain.user.data.UserDataService
import com.elmenus.checkout.domain.user.model.{Credentials, User}
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserDataServiceImpl(userReactiveRepository: UserReactiveRepository) extends UserDataService {

    override def getByIdentifier(identifier: String): Mono[User] = userReactiveRepository.findByUsername(identifier)

    override def userExists(identifier: String): Mono[Boolean] = userReactiveRepository.existsByUsername(identifier)

    override def validateCredentials(credentials: Credentials): Mono[Boolean] = Mono
        .just(credentials)
        .flatMap(credentials => Mono.zip(
            Mono.just(credentials.username).flatMap(userReactiveRepository.findByUsername).map(_.password),
            Mono.just(credentials.password),
            (correctPassword: String, loginPassword: String) => correctPassword == loginPassword
        ))
        .defaultIfEmpty(false)
}
