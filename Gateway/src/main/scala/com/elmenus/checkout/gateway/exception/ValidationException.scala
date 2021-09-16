package com.elmenus.checkout.gateway.exception

import com.elmenus.checkout.common.exception.Error
import com.elmenus.checkout.common.exception.badrequest.BadRequestException
import org.springframework.context.support.DefaultMessageSourceResolvable
import org.springframework.validation.{BindingResult, FieldError}

import scala.jdk.CollectionConverters.ListHasAsScala
import scala.reflect.ClassTag

class ValidationException(bindingResult: BindingResult)
    extends BadRequestException(
        message = s"One or more fields are invalid in object ${
            Option(bindingResult.getTarget).map(_.getClass).map(_.getSimpleName).getOrElse("")
        }",
        errorType = Error.InvalidBody
    ) {

    def cast[T: ClassTag](o: Any): Option[T] = o match {
        case v: T => Some(v)
        case _ => None
    }

    val invalidFields: List[InvalidField] = bindingResult
        .getAllErrors
        .asScala
        .toList
        .groupBy(error => error.getArguments.last.asInstanceOf[DefaultMessageSourceResolvable].getDefaultMessage)
        .toList
        .map(fieldNameAndErrors => {
            val fieldName = fieldNameAndErrors._1
            val errors = fieldNameAndErrors._2
            val rejectedValue = errors.last.asInstanceOf[FieldError].getRejectedValue
            InvalidField(fieldName, rejectedValue, errors.filter(_ != null).map(_.getDefaultMessage))
        })

    case class InvalidField(name: String, rejectedValue: Any, messages: List[String])
}
