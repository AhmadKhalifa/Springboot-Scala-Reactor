package com.elmenus.checkout.domain.test.utils

import com.elmenus.checkout.domain.base.BaseUseCase

class UseCaseTestSuite[T <: BaseUseCase[_, _]] extends UnitTestSuite {

    var useCase: T = _
}
