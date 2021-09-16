package com.elmenus.checkout.common.exception

import org.springframework.http.HttpStatus

object Error extends Enumeration {

    case class Type(minorCode: Int, httpStatus: HttpStatus) extends super.Val

    val UnknownException: Type = Type(0, HttpStatus.INTERNAL_SERVER_ERROR)
    val InternalServerError: Type = Type(1, HttpStatus.INTERNAL_SERVER_ERROR)
    val NotFound: Type = Type(2, HttpStatus.NOT_FOUND)
    val BadRequest: Type = Type(3, HttpStatus.BAD_REQUEST)
    val Unauthorized: Type = Type(4, HttpStatus.UNAUTHORIZED)
    val MissingAuthToken: Type = Type(5, HttpStatus.UNAUTHORIZED)
    val ExpiredAuthToken: Type = Type(6, HttpStatus.UNAUTHORIZED)
    val InvalidAuthToken: Type = Type(7, HttpStatus.UNAUTHORIZED)
    val DatabaseException: Type = Type(8, HttpStatus.INTERNAL_SERVER_ERROR)
    val UnprocessableEntity: Type = Type(9, HttpStatus.UNPROCESSABLE_ENTITY)
    val InvalidBody: Type = Type(10, HttpStatus.BAD_REQUEST)
    val IncorrectPassword: Type = Type(11, HttpStatus.BAD_REQUEST)
}
