package com.elmenus.checkout.gateway.order.mapper

import com.elmenus.checkout.domain.payment.model.Payment
import com.elmenus.checkout.gateway.order.dto.PaymentDTO
import org.springframework.stereotype.Component

@Component
class PaymentMapper {

    def toDto(payment: Payment): PaymentDTO = new PaymentDTO(payment.key, payment.amount, payment.state)
}
