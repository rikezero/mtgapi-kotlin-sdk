package com.rikezero.mtgapi_kotlin_sdk.domain.model.lists

import com.rikezero.mtgapi_kotlin_sdk.domain.model.card.CardModel
import kotlinx.serialization.Serializable

/**
 * Domain model representing a paginated list of Magic: The Gathering cards.
 *
 * Returned by [com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetCardsUseCase] and the
 * underlying repository when querying multiple cards at once.
 *
 * @property cards The list of [CardModel] objects returned by the query.
 */
@Serializable
data class CardListModel(
    val cards: List<CardModel>
)