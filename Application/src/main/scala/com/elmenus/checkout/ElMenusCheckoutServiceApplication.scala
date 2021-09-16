package com.elmenus.checkout

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

// Once again, it should be scanBasePackages = Array[String]("com.elmenus.checkout.*") but it doesn't work
@SpringBootApplication
class ElMenusCheckoutServiceApplication

object ElMenusCheckoutServiceApplication extends App {
    SpringApplication.run(classOf[ElMenusCheckoutServiceApplication], args: _*)
}
