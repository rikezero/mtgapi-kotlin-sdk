package com.rikezero.mtgapi_kotlin_sdk.domain.model.card

import kotlinx.serialization.Serializable

/**
 * Domain model representing an official ruling for a Magic: The Gathering card.
 *
 * Rulings are official clarifications issued by Wizards of the Coast regarding how a card
 * should be played. They are included in [CardModel.rulings].
 *
 * @property date The date the ruling was issued, formatted as "YYYY-MM-DD".
 * @property text The text of the ruling describing the official clarification.
 */
@Serializable
data class RulingModel(
    val date: String?,
    val text: String?
)
