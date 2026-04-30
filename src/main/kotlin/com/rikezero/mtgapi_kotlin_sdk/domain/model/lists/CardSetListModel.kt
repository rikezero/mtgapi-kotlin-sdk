package com.rikezero.mtgapi_kotlin_sdk.domain.model.lists

import com.rikezero.mtgapi_kotlin_sdk.domain.model.set.CardSetModel
import kotlinx.serialization.Serializable

@Serializable
data class CardSetListModel(
    val sets: List<CardSetModel>
)
