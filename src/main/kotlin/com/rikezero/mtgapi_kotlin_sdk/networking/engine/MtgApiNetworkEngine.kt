package com.rikezero.mtgapi_kotlin_sdk.networking.engine

import com.google.gson.JsonDeserializer
import com.rikezero.mtgapi_kotlin_sdk.networking.response.MtgApiError
import com.rikezero.mtgapi_kotlin_sdk.networking.response.MtgApiResponse
import kotlin.jvm.Throws
import kotlin.reflect.KClass

/**
 * Core HTTP execution engine for the MTG API SDK.
 *
 * Responsible for making the actual network call and returning a raw [MtgApiResponse].
 * Implementations wrap a concrete HTTP client (e.g., Retrofit + OkHttp) and translate
 * HTTP errors into [MtgApiError] exceptions. This interface should not be called directly
 * by application code — use the repository or use-case layer instead.
 */
interface MtgApiNetworkEngine {

    /**
     * Executes an HTTP GET request and returns the deserialized response.
     *
     * @param T The expected response body type; must be a non-null type.
     * @param url The full URL of the resource to fetch.
     * @param headers Additional HTTP headers to include in the request.
     * @param queryParams URL query parameters to append to the request URL.
     * @param responseClass The [KClass] of the expected response type, used for deserialization.
     * @param deserializer An optional custom Gson [JsonDeserializer] for [T]; `null` uses the default deserializer.
     * @return [MtgApiResponse] wrapping the deserialized response body, which may be `null` if the server returns no body.
     * @throws MtgApiError if the network request fails or the server returns an error HTTP status.
     */
    @Throws(MtgApiError::class)
    suspend fun <T : Any> get(
        url: String,
        headers: Map<String, String> = emptyMap(),
        queryParams: Map<String, String> = emptyMap(),
        responseClass: KClass<T>,
        deserializer: JsonDeserializer<T>? = null
    ): MtgApiResponse<T?>
}