package com.rikezero.mtgapi_kotlin_sdk.networking.engine.okhttp

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

internal const val DEFAULT_TIMEOUT = 30000L
internal const val DEFAULT_PING_INTERVAL = 30000L

internal fun buildMtgApiHttpClient(
    interceptors: List<Interceptor>? = null,
    timeout: Long? =  DEFAULT_TIMEOUT,
    pingInterval: Long? = DEFAULT_PING_INTERVAL,
    okHttpClientConfigs: (OkHttpClient.Builder.() -> Unit)? = null,
): OkHttpClient =
    OkHttpClient.Builder().run {
        connectTimeout(timeout ?: DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
        readTimeout(timeout ?: DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
        writeTimeout(timeout ?: DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
        pingInterval?.let { pingInterval(pingInterval, TimeUnit.MILLISECONDS) }
        retryOnConnectionFailure(true)
        interceptors?.forEach { addInterceptor(it) }
        okHttpClientConfigs?.run { okHttpClientConfigs() }
        build()
    }