package com.example.network.error

interface GeneralExceptionHandler {

    fun handleException(ex: Throwable): Throwable
}
