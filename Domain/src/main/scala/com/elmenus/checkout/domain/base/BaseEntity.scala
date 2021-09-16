package com.elmenus.checkout.domain.base

import javax.persistence.{GeneratedValue, GenerationType, Id, MappedSuperclass}
import scala.beans.BeanProperty

@MappedSuperclass
class BaseEntity extends Serializable {

    @Id
    @BeanProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = _
}
