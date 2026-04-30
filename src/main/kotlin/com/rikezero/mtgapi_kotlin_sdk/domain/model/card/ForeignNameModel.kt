package com.rikezero.mtgapi_kotlin_sdk.domain.model.card

import kotlinx.serialization.Serializable

@Serializable
data class ForeignNameModel(
    val name: String?,
    val language: String?,
    val multiverseid: Int?
)
