package com.rikezero.mtgapi_kotlin_sdk.networking.deserializer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.ToNumberPolicy
import java.lang.reflect.Type
import kotlin.reflect.KClass

object MtgApiDeserializer {

    fun <T : Any> getGson(
        responseKClass: KClass<T>
    ): Gson = GsonBuilder()
        .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
        .enableComplexMapKeySerialization()
        .apply {
            registerTypeAdapter(
                responseKClass::class.java,
                object : JsonDeserializer<T> {
                    override fun deserialize(
                        json: JsonElement?,
                        typeOfT: Type?,
                        context: JsonDeserializationContext?
                    ): T {
                        TODO("Not yet implemented")
                    }

                }
            )
        }
        .create()
}