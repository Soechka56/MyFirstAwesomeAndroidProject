package com.example.network.error.impl

import com.example.network.error.GeneralExceptionHandler
import com.example.network.pojo.BrawlStarsErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.net.SocketTimeoutException

class BrawlStarsExceptionHandlerImpl : GeneralExceptionHandler {
    private val gson = Gson()

    override fun handleException(ex: Throwable): Throwable {
        if (ex is BrawlStarsHandledException) return ex

        return when (ex) {
            is SocketTimeoutException -> BrawlStarsHandledException("timeout")
            is HttpException -> {
                when (ex.code()) {
                    403 -> checkForbiddenReason(ex)
                    404 -> BrawlStarsHandledException("not found")
                    500 -> BrawlStarsHandledException("server error")
                    else -> ex
                }
            }
            else -> ex
        }
    }

    private fun checkForbiddenReason(ex: HttpException): Throwable {
        val body = ex.response()?.errorBody()?.string()
            ?: return BrawlStarsHandledException(ex.message())

        val errorResponseJson = runCatching {
            gson.fromJson(body, BrawlStarsErrorResponse::class.java)
        }.getOrNull() ?: return BrawlStarsHandledException(ex.message())

        return BrawlStarsHandledException(
            errorResponseJson.reason ?: errorResponseJson.type ?: errorResponseJson.message ?: ex.message()
        )
    }

    private class BrawlStarsHandledException(message: String?) : RuntimeException(message)
}
