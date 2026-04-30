package com.rikezero.mtgapi_kotlin_sdk.networking.engine.impl

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonSyntaxException
import com.rikezero.mtgapi_kotlin_sdk.networking.deserializer.MtgApiDeserializer
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.MtgApiNetworkEngine
import com.rikezero.mtgapi_kotlin_sdk.networking.response.MtgApiResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.service.MtgApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Retrofit
import kotlin.reflect.KClass

class MtgApiNetworkEngineImpl(
    private val retrofit: Retrofit
): MtgApiNetworkEngine {
    private val mtgApiService: MtgApiService = retrofit.create(MtgApiService::class.java)

    override suspend fun <T : Any> get(
        url: String,
        headers: Map<String, String>,
        queryParams: Map<String, String>,
        responseClass: KClass<T>,
        deserializer: JsonDeserializer<T>?
    ): MtgApiResponse<T?> = withContext(Dispatchers.IO) {
        try {
            val responseBody = mtgApiService.get(url, headers, queryParams)
            parseResponse(responseBody, responseClass)
        } catch (e: Exception) {
            MtgApiResponse.Error(e)
        }
    }

    private fun <T : Any> parseResponse(
        responseBody: ResponseBody,
        responseClass: KClass<T>,
        deserializer: JsonDeserializer<T>? = null
    ): MtgApiResponse<T> {
        return try {
            val responseString = responseBody.string()
            val gsonInstance = if (deserializer != null) {
                GsonBuilder()
                    .registerTypeAdapter(responseClass::class.java, deserializer)
                    .create()
            } else {
                MtgApiDeserializer.getGson(responseClass)
            }
            val parsedResponse = gsonInstance.fromJson<T>(responseString, responseClass::class.java)
            MtgApiResponse.Success(parsedResponse)
        } catch (e: JsonSyntaxException) {
            MtgApiResponse.Error(e)
        }
    }
}