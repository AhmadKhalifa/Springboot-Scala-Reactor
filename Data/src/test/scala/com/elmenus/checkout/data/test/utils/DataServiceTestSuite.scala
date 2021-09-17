package com.elmenus.checkout.data.test.utils

import com.elmenus.checkout.domain.base.BaseDataService

class DataServiceTestSuite[T <: BaseDataService] extends UnitTestSuite {

    var dataService: T = _
}
