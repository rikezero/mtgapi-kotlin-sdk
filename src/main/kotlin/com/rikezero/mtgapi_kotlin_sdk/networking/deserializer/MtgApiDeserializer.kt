package com.rikezero.mtgapi_kotlin_sdk.networking.deserializer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.ToNumberPolicy
import kotlin.reflect.KClass

object MtgApiDeserializer {

    fun <T : Any> getGson(
        responseKClass: KClass<T>
    ): Gson = GsonBuilder()
        .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
        .enableComplexMapKeySerialization()
        .create()
}