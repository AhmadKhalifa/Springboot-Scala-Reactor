package com.elmenus.checkout.application.test.cucumber.utils

import com.elmenus.checkout.data.item.repository.{BasketItemRepository, ItemRepository}
import com.elmenus.checkout.data.order.repository.OrderRepository
import com.elmenus.checkout.data.payment.repository.PaymentRepository
import com.elmenus.checkout.data.user.repository.UserRepository
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles

import javax.annotation.PostConstruct

@SpringBootTest
@ActiveProfiles(Array("test"))
@AutoConfigureWebTestClient
@CucumberContextConfiguration
abstract class BaseStepDefinitions {

    @Autowired
    var scenarioData: ScenarioData = _

    @Autowired
    var passwordEncoder: PasswordEncoder = _

    @Autowired
    var webClient: WebClient = _

    @Autowired
    var orderRepository: OrderRepository = _

    @Autowired
    var paymentRepository: PaymentRepository = _

    @Autowired
    var basketItemRepository: BasketItemRepository = _

    @Autowired
    var itemRepository: ItemRepository = _

    @Autowired
    var userRepository: UserRepository = _

    var repositories: List[JpaRepository[_, _]] = _

    @PostConstruct
    def initialize(): Unit = repositories = List(
        orderRepository,
        paymentRepository,
        basketItemRepository,
        itemRepository,
        userRepository
    )
}
