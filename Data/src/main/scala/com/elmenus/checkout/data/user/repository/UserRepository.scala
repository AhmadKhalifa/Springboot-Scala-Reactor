package com.elmenus.checkout.data.user.repository

import com.elmenus.checkout.domain.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import java.util.Optional

@Repository
trait UserRepository extends JpaRepository[User, java.lang.Long] {

    def findByUsername(username: String): Optional[User]

    def existsByUsername(username: String): Boolean
}
