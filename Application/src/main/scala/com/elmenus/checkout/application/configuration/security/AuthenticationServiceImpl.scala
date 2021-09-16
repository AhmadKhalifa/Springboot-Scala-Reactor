package com.elmenus.checkout.application.configuration.security

import com.elmenus.checkout.application.configuration.security.properties.JwtProperties
import com.elmenus.checkout.common.exception.autorization.{AuthorizationException, ExpiredTokenException, InvalidTokenException}
import com.elmenus.checkout.common.exception.base.BusinessException
import com.elmenus.checkout.domain.authentication.data.AuthenticationService
import com.elmenus.checkout.domain.authentication.model.Token.{AUTH_HEADER_PREFIX, KEY_ROLE, KEY_USER_ID}
import com.elmenus.checkout.domain.authentication.model.{JwtAuthentication, Token}
import com.elmenus.checkout.domain.user.model.{User, UserRole}
import io.jsonwebtoken.{Claims, ExpiredJwtException, Jwts}
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

import java.util
import java.util.Date
import javax.crypto.SecretKey

@Service
class AuthenticationServiceImpl(jwtProperties: JwtProperties) extends AuthenticationService {

    private val signingKey: SecretKey = Keys.hmacShaKeyFor(jwtProperties.secret.getBytes())

    private def getAllClaimsFromToken(token: String): Mono[Claims] = Mono
        .fromCallable(() =>
            Jwts
                .parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token.withoutPrefix)
                .getBody
        )

    private def mapExceptions(throwable: Throwable): AuthorizationException =
        if (throwable.isInstanceOf[ExpiredJwtException]) new ExpiredTokenException(rootException = throwable)
        else new InvalidTokenException(rootException = throwable)

    private def getClaimFromToken[T](token: String, claimsResolver: Claims => T): Mono[T] = Mono
        .just(token)
        .flatMap(getAllClaimsFromToken)
        .onErrorMap(mapExceptions)
        .map(claimsResolver.apply(_))

    override def getCurrentAuthentication: Mono[JwtAuthentication] = ReactiveSecurityContextHolder
        .getContext
        .map(_.getAuthentication)
        .map(_.asInstanceOf[JwtAuthentication])

    override def authenticate(token: String): Mono[JwtAuthentication] = Mono
        .zip(
            getUserIdFromToken(token),
            getUsernameFromToken(token),
            getRoleFromToken(token)
        )
        .map(details => {
            "".toLong
            JwtAuthentication(details.getT1, details.getT2, details.getT3)
        })

    override def getUsernameFromToken(token: String): Mono[String] =
        getClaimFromToken(token, claims => claims.getSubject)
            .validate(_.nonEmpty)

    override def getRoleFromToken(token: String): Mono[UserRole.Value] =
        getClaimFromToken(token, KEY_ROLE, classOf[String])
            .validate(UserRole.isRole)
            .map(UserRole.valueOf)

    override def getUserIdFromToken(token: String): Mono[Long] =
        getClaimFromToken(token, KEY_USER_ID, classOf[String])
            .validate(_.nonEmpty)
            .map(_.toLong)

    override def getExpirationDateFromToken(token: String): Mono[Date] =
        getClaimFromToken(token, claims => claims.getExpiration)

    override def getClaimFromToken[T](token: String, claimKey: String, clazz: Class[T]): Mono[T] =
        getAllClaimsFromToken(token)
            .map(claims => claims.get(claimKey, clazz))
            .onErrorMap(mapExceptions)

    override def generateToken(user: User): Mono[Token] = Mono.zip(
        Mono.just(user),
        Mono.defer(() => Mono.just(new Date(System.currentTimeMillis() + jwtProperties.expiration)))
    ).flatMap(userAndExpirationDate => {
        val user = userAndExpirationDate.getT1
        val expirationDate = userAndExpirationDate.getT2
        Mono
            .fromCallable(() => Jwts
                .builder()
                .setSubject(user.username)
                .setClaims(new util.TreeMap[String, Object]() {{
                    put(KEY_USER_ID, user.id.toString)
//                    put(KEY_ROLE, user.role.toString)
                }})
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(signingKey)
                .compact()
            )
            .map(Token(_, expirationDate))
    })

    protected implicit class TokenHasWithoutPrefix(token: String) {

        def withoutPrefix: String =
            if (token.startsWith(AUTH_HEADER_PREFIX)) token.substring(AUTH_HEADER_PREFIX.length)
            else token
    }

    protected implicit class MonoHasValidate[A](mono: Mono[A]) {

        def validate(validationFunction: A => Boolean,
                     exception: BusinessException = new AuthorizationException()): Mono[A] = mono
            .flatMap(value =>
                Mono
                    .justOrEmpty(value)
                    .map(validationFunction(_))
                    .flatMap(isValid =>
                        if (isValid) Mono.justOrEmpty(value)
                        else Mono.error(exception)
                    )
            )
    }
}
