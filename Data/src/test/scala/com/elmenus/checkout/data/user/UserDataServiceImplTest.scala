package com.elmenus.checkout.data.user

import com.elmenus.checkout.data.test.utils.{DataFactory, DataServiceTestSuite}
import com.elmenus.checkout.data.user.repository.UserReactiveRepository
import org.junit.jupiter.api.{BeforeEach, Test}
import org.mockito.Mock
import org.mockito.Mockito.when
import reactor.test.StepVerifier

class UserDataServiceImplTest extends DataServiceTestSuite[UserDataServiceImpl] {

    @Mock
    var userReactiveRepository: UserReactiveRepository = _

    @BeforeEach
    def injectMocks(): Unit = dataService = new UserDataServiceImpl(userReactiveRepository)

    // getByIdentifier

    @Test
    def `test getByIdentifier. Given an identifier for an existing user, when queried from db, then it should return the user`(): Unit = {
        val user = DataFactory.generateUser()
        val username = user.username

        when(userReactiveRepository.findByUsername(username)).thenReturnMono(user)

        StepVerifier
            .create(dataService.getByIdentifier(username))
            .expectNext(user)
            .verifyComplete()
    }

    @Test
    def `test getByIdentifier. Given an identifier for a non-existing user, when queried from db, then it should return an empty mono`(): Unit = {
        val username = DataFactory.generateString

        when(userReactiveRepository.findByUsername(username)).thenReturnEmptyMono

        StepVerifier
            .create(dataService.getByIdentifier(username))
            .verifyComplete()
    }

    // userExists

    @Test
    def `test userExists. Given an identifier for an existing user, when queried from db, then it should return true`(): Unit = {
        val userExists = true
        val username = DataFactory.generateUser().username

        when(userReactiveRepository.existsByUsername(username)).thenReturnMono(userExists)

        StepVerifier
            .create(dataService.userExists(username))
            .expectNext(userExists)
            .verifyComplete()
    }

    @Test
    def `test userExists. Given an identifier for a non-existing user, when queried from db, then it should return false`(): Unit = {
        val userExists = false
        val username = DataFactory.generateUser().username

        when(userReactiveRepository.existsByUsername(username)).thenReturnMono(userExists)

        StepVerifier
            .create(dataService.userExists(username))
            .expectNext(userExists)
            .verifyComplete()
    }

    // validateCredentials

    @Test
    def `test validateCredentials. Given valid credentials, when validated, then it should return true`(): Unit = {
        val isValidCredentials = true
        val rightCredentials = DataFactory.generateCredentials()
        val credentials = DataFactory.generateCredentials(rightCredentials.username, rightCredentials.password)
        val user = DataFactory.generateUser(rightCredentials.username, rightCredentials.password)

        when(userReactiveRepository.findByUsername(credentials.username)).thenReturnMono(user)

        StepVerifier
            .create(dataService.validateCredentials(credentials))
            .expectNext(isValidCredentials)
            .verifyComplete()
    }

    @Test
    def `test validateCredentials. Given invalid credentials (wrong password), when validated, then it should return false`(): Unit = {
        val isValidCredentials = false
        val rightCredentials = DataFactory.generateCredentials()
        val credentials = DataFactory.generateCredentials(rightCredentials.username)
        val user = DataFactory.generateUser(rightCredentials.username, rightCredentials.password)

        when(userReactiveRepository.findByUsername(credentials.username)).thenReturnMono(user)

        StepVerifier
            .create(dataService.validateCredentials(credentials))
            .expectNext(isValidCredentials)
            .verifyComplete()
    }

    @Test
    def `test validateCredentials. Given invalid credentials (non-existing user), when validated, then it should return false`(): Unit = {
        val isValidCredentials = false
        val rightCredentials = DataFactory.generateCredentials()
        val credentials = DataFactory.generateCredentials()
        val user = DataFactory.generateUser(rightCredentials.username, rightCredentials.password)

        when(userReactiveRepository.findByUsername(credentials.username)).thenReturnMono(user)

        StepVerifier
            .create(dataService.validateCredentials(credentials))
            .expectNext(isValidCredentials)
            .verifyComplete()
    }
}
