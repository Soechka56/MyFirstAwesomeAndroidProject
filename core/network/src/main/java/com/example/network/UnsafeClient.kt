package ru.itis.android.uprising26.network

import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

fun getUnsafeOkHttpClientBuilder(): OkHttpClient.Builder {
    val okHttpClient = OkHttpClient.Builder()
    try {
        val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {

            override fun checkClientTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun checkServerTrusted(
                chain: Array<out X509Certificate>?,
                authType: String?
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        val sslSocketFactory = sslContext.socketFactory
        if (trustAllCerts.isNotEmpty() && trustAllCerts.first() is X509TrustManager) {
            okHttpClient.sslSocketFactory(
                sslSocketFactory,
                trustAllCerts.first() as X509TrustManager
            )
            okHttpClient.hostnameVerifier { _, _ -> true }
        }

        return okHttpClient
    } catch (e: Exception) {
        return okHttpClient
    }
}