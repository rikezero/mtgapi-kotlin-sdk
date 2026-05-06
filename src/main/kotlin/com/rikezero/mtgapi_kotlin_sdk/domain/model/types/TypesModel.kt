package com.rikezero.mtgapi_kotlin_sdk.domain.model.types

import kotlinx.serialization.Serializable

/**
 * Domain model containing all card types available in Magic: The Gathering.
 *
 * Card types appear on the left of the dash in a card's type line (e.g., Creature, Instant, Sorcery).
 *
 * @property types The list of all card type names.
 */
@Serializable
data class TypesModel(
    val types: List<String>
)

/**
 * Domain model containing all card subtypes available in Magic: The Gathering.
 *
 * Subtypes appear on the right of the dash in a card's type line (e.g., Human, Elf, Equipment, Aura).
 *
 * @property subTypes The list of all card subtype names.
 */
@Serializable
data class SubtypesModel(
    val subTypes: List<String>
)

/**
 * Domain model containing all card supertypes available in Magic: The Gathering.
 *
 * Supertypes appear to the far left of the card type line (e.g., Basic, Legendary, Snow, World).
 *
 * @property superTypes The list of all card supertype names.
 */
@Serializable
data class SuperTypesModel(
    val superTypes: List<String>
)