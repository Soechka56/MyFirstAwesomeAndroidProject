package com.soechka1.myfirstawesomeandroidproject.utils


class InvalidHashException(message: String) : Exception(message)
class NetworkException(message: String) : Exception(message)
class TokenExpiredException(message: String) : Exception(message)

object CoroutineError {
    const val ERROR_TOAST = 1
    const val ERROR_SNACKBAR = 2
    const val ERROR_RESET_SETTINGS = 3
}