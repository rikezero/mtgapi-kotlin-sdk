package com.rikezero.mtgapi_kotlin_sdk.domain.model.lists

import com.rikezero.mtgapi_kotlin_sdk.domain.model.card.CardModel
import kotlinx.serialization.Serializable

@Serializable
data class CardListModel(
    val cards: List<CardModel>
)