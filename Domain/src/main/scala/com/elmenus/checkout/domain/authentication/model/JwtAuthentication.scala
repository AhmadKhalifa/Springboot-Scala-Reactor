package com.elmenus.checkout.domain.authentication.model

import com.elmenus.checkout.domain.user.model.UserRole
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority

import scala.jdk.CollectionConverters.SeqHasAsJava

case class JwtAuthentication(userId: Long, username: String, role: UserRole.Value)
    extends UsernamePasswordAuthenticationToken(
        username,
        null,
        List(new SimpleGrantedAuthority(role.toString)).asJava
    )
