package com.elmenus.checkout.application.configuration.security

import com.elmenus.checkout.application.configuration.security.properties.EncoderProperties
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

@Component
class PBKDF2Encoder(private val encoderProperties: EncoderProperties) extends PasswordEncoder {

    override def encode(rawPassword: CharSequence): String = Base64
        .getEncoder
        .encodeToString(
            SecretKeyFactory
                .getInstance(encoderProperties.secretKeyFactoryName)
                .generateSecret(
                    new PBEKeySpec(
                        rawPassword.toString.toCharArray,
                        encoderProperties.secret.getBytes(),
                        encoderProperties.iteration,
                        encoderProperties.keyLength
                    )
                )
                .getEncoded
        )

    override def matches(rawPassword: CharSequence, encodedPassword: String): Boolean =
        encode(rawPassword) == encodedPassword
}
