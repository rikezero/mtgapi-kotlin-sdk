package com.rikezero.mtgapi_kotlin_sdk.domain.model.lists

import com.rikezero.mtgapi_kotlin_sdk.domain.model.set.CardSetModel
import kotlinx.serialization.Serializable

/**
 * Domain model representing a list of Magic: The Gathering sets.
 *
 * Returned by [com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetSetsUseCase] and the
 * underlying repository when querying multiple sets at once.
 *
 * @property sets The list of [CardSetModel] objects returned by the query.
 */
@Serializable
data class CardSetListModel(
    val sets: List<CardSetModel>
)
