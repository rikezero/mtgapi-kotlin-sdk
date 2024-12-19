package com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter.impl

import com.google.gson.JsonDeserializer
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.MtgApiNetworkEngine
import com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter.MtgApiNetworkAdapter
import com.rikezero.mtgapi_kotlin_sdk.networking.response.MtgApiResponse
import kotlin.reflect.KClass

class MtgApiNetworkAdapterImpl(
    private val networkEngine: MtgApiNetworkEngine
): MtgApiNetworkAdapter {
    override suspend fun <T : Any> get(
        url: String,
        headers: Map<String, String>,
        queryParams: Map<String, String>,
        responseClass: KClass<T>,
        deserializer: JsonDeserializer<T>?
    ): MtgApiResponse<T?> {
        return networkEngine.get(
            url = url,
            headers = headers,
            queryParams = queryParams,
            responseClass = responseClass,
            deserializer = deserializer
        )
    }
}