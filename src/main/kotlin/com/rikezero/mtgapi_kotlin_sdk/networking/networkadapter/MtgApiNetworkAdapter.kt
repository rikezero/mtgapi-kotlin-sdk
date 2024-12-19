package com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter

import com.google.gson.JsonDeserializer
import com.rikezero.mtgapi_kotlin_sdk.networking.response.MtgApiResponse
import kotlin.reflect.KClass

interface MtgApiNetworkAdapter {
    suspend fun <T : Any> get(
        url: String,
        headers: Map<String, String> = emptyMap(),
        queryParams: Map<String, String> = emptyMap(),
        responseClass: KClass<T>,
        deserializer: JsonDeserializer<T>? = null
    ): MtgApiResponse<T?>
}