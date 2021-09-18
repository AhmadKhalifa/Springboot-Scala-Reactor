package com.elmenus.checkout.data.user.repository

import com.elmenus.checkout.data.test.utils.{DataFactory, ReactiveRepositoryTestSuite}
import com.elmenus.checkout.domain.user.model.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.{ArgumentCaptor, Captor, Mock}
import org.mockito.Mockito.{times, verify, when}
import reactor.test.StepVerifier

import java.util.Optional

class UserReactiveRepositoryTest extends ReactiveRepositoryTestSuite[UserReactiveRepository] {

    @Captor
    var userCaptor: ArgumentCaptor[User] = _

    @Mock
    var userRepository: UserRepository = _

    @BeforeEach
    def injectMocks(): Unit = reactiveRepository = new UserReactiveRepository(userRepository) {{
        transactionTemplate = UserReactiveRepositoryTest.this.transactionTemplate
        jdbcScheduler = UserReactiveRepositoryTest.this.jdbcScheduler
    }}

    // findById

    @Test
    def `test findById. Given existing userId, when queried, then the user is returned`(): Unit = {
        val user = DataFactory.generateUser()
        val userId = user.id

        when(userRepository.findById(userId)).thenReturn(Optional.of(user))

        StepVerifier
            .create(reactiveRepository.findById(userId))
            .expectNext(user)
            .verifyComplete()
    }

    @Test
    def `test findById. Given non-existing userId, when queried, then an empty mono is returned`(): Unit = {
        val user: User = null
        val userId = DataFactory.generateLong

        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user))

        StepVerifier
            .create(reactiveRepository.findById(userId))
            .verifyComplete()
    }

    // existsById

    @Test
    def `test existsById. Given existing userId, when checked, then true is returned`(): Unit = {
        val userExists = true
        val user = DataFactory.generateUser()
        val userId = user.id

        when(userRepository.findById(userId)).thenReturn(Optional.of(user))

        StepVerifier
            .create(reactiveRepository.existsById(userId))
            .expectNext(userExists)
            .verifyComplete()
    }

    @Test
    def `test existsById. Given non-existing userId, when checked, then false is returned`(): Unit = {
        val userExists = false
        val user = null
        val userId = DataFactory.generateLong

        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user))

        StepVerifier
            .create(reactiveRepository.existsById(userId))
            .expectNext(userExists)
            .verifyComplete()
    }

    // save

    @Test
    def `test save. Given user, when saved, then the updated user is returned`(): Unit = {
        stubTransactionTemplate()

        val user = DataFactory.generateUser()

        when(userRepository.save(user)).thenReturn(user)

        StepVerifier
            .create(reactiveRepository.save(user))
            .expectNext(user)
            .verifyComplete()

        verify(userRepository, times(1)).save(userCaptor.capture())
        assertThat(userCaptor.getValue).isEqualTo(user)
    }

    // findByUsername

    @Test
    def `test findByUsername. Given existing username, when queried, then the user is returned`(): Unit = {
        val user = DataFactory.generateUser()
        val username = user.username

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user))

        StepVerifier
            .create(reactiveRepository.findByUsername(username))
            .expectNext(user)
            .verifyComplete()
    }

    @Test
    def `test findByUsername. Given non-existing username, when queried, then an empty mono is returned`(): Unit = {
        val user: User = null
        val username = DataFactory.generateString

        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(user))

        StepVerifier
            .create(reactiveRepository.findByUsername(username))
            .verifyComplete()
    }

    // existsByUsername

    @Test
    def `test existsByUsername. Given existing username, when checked, then true is returned`(): Unit = {
        val userExists = true
        val user = DataFactory.generateUser()
        val username = user.username

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user))

        StepVerifier
            .create(reactiveRepository.existsByUsername(username))
            .expectNext(userExists)
            .verifyComplete()
    }

    @Test
    def `test existsByUsername. Given non-existing username, when checked, then false is returned`(): Unit = {
        val userExists = false
        val user = null
        val username = DataFactory.generateString

        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(user))

        StepVerifier
            .create(reactiveRepository.existsByUsername(username))
            .expectNext(userExists)
            .verifyComplete()
    }
}
