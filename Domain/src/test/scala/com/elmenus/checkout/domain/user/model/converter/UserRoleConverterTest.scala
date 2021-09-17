package com.elmenus.checkout.domain.user.model.converter

import com.elmenus.checkout.domain.test.utils.UnitTestSuite
import com.elmenus.checkout.domain.user.model.UserRole
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.{BeforeEach, Test}

class UserRoleConverterTest extends UnitTestSuite {

    var userRoleConverter: UserRoleConverter = _

    @BeforeEach
    def injectMocks(): Unit = userRoleConverter = new UserRoleConverter()

    @Test
    def `Given CUSTOMER as enum, when its converted to database column, then it should be "CUSTOMER" as string`(): Unit = {
        assertThat(userRoleConverter.convertToDatabaseColumn(UserRole.CUSTOMER))
            .isEqualTo(UserRole.CUSTOMER.toString)
    }

    @Test
    def `Given "CUSTOMER" as String, when its converted to entity attribute, then it should be CUSTOMER as enum`(): Unit = {
        assertThat(userRoleConverter.convertToEntityAttribute(UserRole.CUSTOMER.toString).id)
            .isEqualTo(UserRole.CUSTOMER.id)
    }
}
