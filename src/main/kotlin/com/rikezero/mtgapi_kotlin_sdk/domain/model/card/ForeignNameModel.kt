package com.rikezero.mtgapi_kotlin_sdk.domain.model.card

import kotlinx.serialization.Serializable

/**
 * Domain model representing a card's name and identifier in a non-English language.
 *
 * Foreign name entries are included in [CardModel.foreignNames] for cards that have been
 * printed in languages other than English.
 *
 * @property name The card name in the given [language].
 * @property language The language in which the card was printed (e.g., "German", "Japanese").
 * @property multiverseid The Multiverse ID of this foreign printing on Wizards' Gatherer website.
 */
@Serializable
data class ForeignNameModel(
    val name: String?,
    val language: String?,
    val multiverseid: Int?
)
