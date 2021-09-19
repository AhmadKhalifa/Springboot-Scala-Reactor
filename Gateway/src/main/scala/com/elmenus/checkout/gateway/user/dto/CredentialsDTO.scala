package com.elmenus.checkout.gateway.user.dto

import javax.validation.constraints.NotBlank
import scala.beans.BeanProperty

class CredentialsDTO {

    @NotBlank
    @BeanProperty
    var username: String = _

    @NotBlank
    @BeanProperty
    var password: String = _

    def this(username: String, password: String) = {
        this()
        this.username = username
        this.password = password
    }
}
