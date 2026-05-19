package com.example.network.error

inline fun <T, R> T.runCatching(
    generalExceptionHandler: GeneralExceptionHandler,
    block: T.() -> R
): R {
    return try {
        block()
    } catch (e: Throwable) {
        throw generalExceptionHandler.handleException(ex = e)
    }
}
