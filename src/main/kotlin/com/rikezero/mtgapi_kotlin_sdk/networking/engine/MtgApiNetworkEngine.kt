package com.rikezero.mtgapi_kotlin_sdk.networking.engine

import com.google.gson.JsonDeserializer
import com.rikezero.mtgapi_kotlin_sdk.networking.response.MtgApiError
import com.rikezero.mtgapi_kotlin_sdk.networking.response.MtgApiResponse
import kotlin.jvm.Throws
import kotlin.reflect.KClass

interface MtgApiNetworkEngine {
    @Throws(MtgApiError::class)
    suspend fun <T : Any> get(
        url: String,
        headers: Map<String, String> = emptyMap(),
        queryParams: Map<String, String> = emptyMap(),
        responseClass: KClass<T>,
        deserializer: JsonDeserializer<T>? = null
    ): MtgApiResponse<T?>
}