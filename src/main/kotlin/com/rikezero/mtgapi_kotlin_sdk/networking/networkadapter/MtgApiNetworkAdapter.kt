package com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter

import com.google.gson.JsonDeserializer
import com.rikezero.mtgapi_kotlin_sdk.networking.response.MtgApiResponse
import kotlin.reflect.KClass

/**
 * Adapter layer between the high-level [com.rikezero.mtgapi_kotlin_sdk.networking.MtgApiNetworking]
 * interface and the underlying [com.rikezero.mtgapi_kotlin_sdk.networking.engine.MtgApiNetworkEngine].
 *
 * Implementations are responsible for constructing the final request (merging headers, query
 * parameters, base URL, etc.) and delegating execution to the engine. This abstraction makes
 * the networking layer independently testable and swappable.
 */
interface MtgApiNetworkAdapter {

    /**
     * Performs an HTTP GET request and deserializes the response body.
     *
     * @param T The expected response body type; must be a non-null type.
     * @param url The full URL of the resource to fetch.
     * @param headers Additional HTTP headers to include in the request.
     * @param queryParams URL query parameters to append to the request URL.
     * @param responseClass The [KClass] of the expected response type, used for deserialization.
     * @param deserializer An optional custom Gson [JsonDeserializer] for [T]; `null` uses the default deserializer.
     * @return [MtgApiResponse] wrapping the deserialized response body, which may be `null` if the server returns no body.
     */
    suspend fun <T : Any> get(
        url: String,
        headers: Map<String, String> = emptyMap(),
        queryParams: Map<String, String> = emptyMap(),
        responseClass: KClass<T>,
        deserializer: JsonDeserializer<T>? = null
    ): MtgApiResponse<T?>
}