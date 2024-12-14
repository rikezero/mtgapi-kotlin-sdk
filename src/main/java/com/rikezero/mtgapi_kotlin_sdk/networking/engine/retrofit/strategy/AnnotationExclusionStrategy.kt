package com.rikezero.mtgapi_kotlin_sdk.networking.engine.retrofit.strategy

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

class AnnotationExclusionStrategy: ExclusionStrategy {
    override fun shouldSkipField(f: FieldAttributes): Boolean {
        return f.getAnnotation(HideFromSerialization::class.java) != null
    }

    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
        return false
    }

    @Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.FIELD)
    annotation class HideFromSerialization {}
}