package com.elmenus.checkout.data.payment.repository

import com.elmenus.checkout.domain.payment.model.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import java.util.Optional

@Repository
trait PaymentRepository extends JpaRepository[Payment, java.lang.Long] {

    def findByKey(key: String): Optional[Payment]
}
