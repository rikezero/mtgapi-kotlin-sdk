package com.rikezero.mtgapi_kotlin_sdk.domain.model.set

import com.rikezero.mtgapi_kotlin_sdk.domain.model.card.CardModel
import kotlinx.serialization.Serializable

@Serializable
data class CardSetModel(
    val code: String?,
    val name: String?,
    val type: String?,
    val booster: List<CardModel>?,
    val releaseDate: String?,
    val block: String?,
    val onlineOnly: Boolean?
)
