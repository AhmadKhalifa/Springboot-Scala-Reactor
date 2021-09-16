package com.elmenus.checkout.gateway.business.user.dto

import javax.validation.constraints.NotBlank
import scala.beans.BeanProperty

class CredentialsDTO {

    @NotBlank
    @BeanProperty
    var username: String = _

    @NotBlank
    @BeanProperty
    var password: String = _
}
