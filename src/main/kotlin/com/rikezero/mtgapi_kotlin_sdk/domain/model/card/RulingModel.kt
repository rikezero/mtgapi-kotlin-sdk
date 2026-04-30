package com.rikezero.mtgapi_kotlin_sdk.domain.model.card

import kotlinx.serialization.Serializable

@Serializable
data class RulingModel(
    val date: String?,
    val text: String?
)
