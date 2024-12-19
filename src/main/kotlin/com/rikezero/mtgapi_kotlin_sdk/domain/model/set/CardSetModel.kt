package com.rikezero.mtgapi_kotlin_sdk.domain.model.set

data class CardSetModel(
    val code: String?,
    val name: String?,
    val type: String?,
    val booster: List<Any>?,
    val releaseDate: String?,
    val block: String?,
    val onlineOnly: Boolean?
)
