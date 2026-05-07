package com.rikezero.mtgapi_kotlin_sdk.domain.model.set

import com.rikezero.mtgapi_kotlin_sdk.domain.model.card.CardModel
import kotlinx.serialization.Serializable

/**
 * Domain model representing a Magic: The Gathering set (expansion).
 *
 * A set groups a collection of cards released together. This model surfaces the most
 * commonly needed set metadata returned by the MTG API.
 *
 * @property code The short set code (e.g., "KTK" for Khans of Tarkir).
 * @property name The full name of the set (e.g., "Khans of Tarkir").
 * @property type The set type (e.g., "expansion", "core", "masters", "promo").
 * @property booster The booster pack composition for the set as a list of card representations.
 * @property releaseDate The official release date of the set, formatted as "YYYY-MM-DD".
 * @property block The block name that this set belongs to, if applicable.
 * @property onlineOnly Whether the set is available exclusively on Magic Online and not in paper.
 */
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
