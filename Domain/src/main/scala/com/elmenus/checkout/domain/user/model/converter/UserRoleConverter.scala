package com.elmenus.checkout.domain.user.model.converter

import com.elmenus.checkout.domain.user.model.UserRole

import javax.persistence.{AttributeConverter, Converter}

@Converter
class UserRoleConverter extends AttributeConverter[UserRole.Value, String] {

    override def convertToDatabaseColumn(attribute: UserRole.Value): String = attribute.toString

    override def convertToEntityAttribute(dbData: String): UserRole.Value = UserRole.valueOf(dbData)
}
