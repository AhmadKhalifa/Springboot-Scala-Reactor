package com.elmenus.checkout.data.test.utils

import com.elmenus.checkout.data.base.ReactiveJpaRepository
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.when
import org.springframework.transaction.support.{TransactionCallback, TransactionTemplate}
import reactor.core.scheduler.{Scheduler, Schedulers}

import java.util.concurrent.Executors

class ReactiveRepositoryTestSuite[T <: ReactiveJpaRepository[_, _]] extends UnitTestSuite {

    protected val threadPool = 1

    @Mock
    protected var transactionTemplate: TransactionTemplate = _

    protected var jdbcScheduler: Scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(threadPool))

    protected var reactiveRepository: T = _

    def stubTransactionTemplate(): Unit = {
        when(transactionTemplate.execute(any(classOf[TransactionCallback[_]]))).thenAnswer(answer => {
            answer.getArguments.last.asInstanceOf[TransactionCallback[_]].doInTransaction(null)
        })
    }

    def injectDependencies(): Unit = {
        reactiveRepository.transactionTemplate = transactionTemplate
        reactiveRepository.jdbcScheduler = jdbcScheduler
    }
}
