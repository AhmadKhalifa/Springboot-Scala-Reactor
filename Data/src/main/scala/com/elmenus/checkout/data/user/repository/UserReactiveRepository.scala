package com.elmenus.checkout.data.user.repository

import com.elmenus.checkout.data.base.ReactiveJpaRepository
import com.elmenus.checkout.domain.user.model.User
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class UserReactiveRepository(userRepository: UserRepository) extends ReactiveJpaRepository[User, UserRepository] {

    override protected def repository: UserRepository = userRepository

    def findByUsername(username: String): Mono[User] = Mono
        .just(username)
        .flatMap(username => Mono.defer(() => Mono.fromCallable(() => repository.findByUsername(username))))
        .flatMap(optional => if (optional.isPresent) Mono.just(optional.get()) else Mono.empty())
        .subscribeOn(jdbcScheduler)

    def existsByUsername(username: String): Mono[Boolean] = findByUsername(username)
        .map(_ => true)
        .defaultIfEmpty(false)
}
