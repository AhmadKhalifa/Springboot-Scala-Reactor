package com.elmenus.checkout.domain.user.model

import com.elmenus.checkout.domain.base.BaseEntity
import com.elmenus.checkout.domain.user.model.converter.UserRoleConverter

import javax.persistence.{Convert, Entity, Table}
import scala.beans.BeanProperty

@Entity
@Table(name = "user")
class User extends BaseEntity {

    @BeanProperty
    var username: String = _

    @BeanProperty
    var password: String = _

    @BeanProperty
    @Convert(converter = classOf[UserRoleConverter])
    var role: UserRole.Value = UserRole.CUSTOMER
}
