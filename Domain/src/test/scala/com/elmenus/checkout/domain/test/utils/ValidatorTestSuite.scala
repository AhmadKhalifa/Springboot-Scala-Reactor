package com.elmenus.checkout.domain.test.utils

import com.elmenus.checkout.domain.base.BaseValidator

class ValidatorTestSuite[T <: BaseValidator[_]] extends UnitTestSuite {

    var validator: T = _
}
