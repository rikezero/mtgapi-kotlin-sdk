package com.rikezero.mtgapi_kotlin_sdk.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class FormatsModel(
    val formats: List<String>
)