package com.elmenus.checkout.data.base

import com.elmenus.checkout.domain.base.BaseEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler

@Component
abstract class ReactiveJpaRepository[E <: BaseEntity, T <: JpaRepository[E, java.lang.Long]] {

    @Autowired
    var transactionTemplate: TransactionTemplate = _

    @Autowired
    var jdbcScheduler: Scheduler = _

    protected def repository: T

    def findById(id: Long): Mono[E] = Mono
        .just(id)
        .flatMap(id => Mono.fromCallable(() => repository.findById(id)))
        .flatMap(optional => if (optional.isPresent) Mono.just(optional.get()) else Mono.empty())
        .subscribeOn(jdbcScheduler)

    def existsById(id: Long): Mono[Boolean] = findById(id)
        .map(_ => true)
        .defaultIfEmpty(false)

    def save(entity: E): Mono[E] = Mono
        .fromCallable(() => transactionTemplate.execute(_ => repository.save(entity)))
        .subscribeOn(jdbcScheduler)

    // And so on...
}
