package com.elmenus.checkout.domain.user.model

object UserRole extends Enumeration {

    val CUSTOMER: UserRole.Value = Value

    def isRole(role: String): Boolean = values.exists(_.toString == role)

    def valueOf(role: String): UserRole.Value = values.filter(_.toString == role.toUpperCase).firstKey
}
