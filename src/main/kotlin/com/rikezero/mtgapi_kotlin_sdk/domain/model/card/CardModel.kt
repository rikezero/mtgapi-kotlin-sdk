package com.rikezero.mtgapi_kotlin_sdk.domain.model.card

import kotlinx.serialization.Serializable

/**
 * Domain model representing a single Magic: The Gathering card.
 *
 * This is the canonical card representation exposed to callers of the SDK.
 * All properties are nullable because not every card in the MTG API response includes
 * every field (e.g., only creatures have [power] and [toughness]; only planeswalkers have [loyalty]).
 *
 * @property name The card name.
 * @property manaCost The mana cost of the card expressed in MTG mana symbols (e.g., "{2}{W}{U}").
 * @property cmc The converted mana cost, always a number.
 * @property colors The colors of the card, usually derived from the casting cost.
 * @property colorIdentity The color identity of the card, by color code (e.g., ["R", "U"]).
 * @property type The full type line of the card as printed (e.g., "Legendary Creature — Human Warrior").
 * @property types The card's types (e.g., Creature, Instant, Sorcery).
 * @property superTypes The card's supertypes (e.g., Basic, Legendary, Snow).
 * @property subTypes The card's subtypes (e.g., Human, Elf, Equipment).
 * @property rarity The rarity of the card (e.g., Common, Uncommon, Rare, Mythic Rare).
 * @property set The set code of the set the card belongs to.
 * @property setName The full name of the set the card belongs to.
 * @property text The Oracle text of the card, which may contain mana symbols.
 * @property flavor The flavor text printed on the card.
 * @property artist The artist credit for the card illustration.
 * @property number The collector number printed on the card; may contain letters (e.g., "123a").
 * @property power The power value for creature cards; may be a non-numeric string (e.g., "1+*").
 * @property toughness The toughness value for creature cards; may be a non-numeric string.
 * @property layout The card layout (e.g., normal, split, flip, double-faced, token).
 * @property multiverseId The Multiverse ID of the card on Wizards' Gatherer website, if available.
 * @property imageUrl The URL of the card image.
 * @property rulings The official rulings associated with this card.
 * @property foreignNames The card's name and Multiverse ID in other languages.
 * @property printings The set codes of all sets in which this card has been printed.
 * @property originalText The original Oracle text of the card at the time of printing.
 * @property originalType The original type line of the card at the time of printing.
 * @property id The unique identifier for this card, generated as an SHA1 hash of set code + card name + card image name.
 */
@Serializable
data class CardModel(
    val name: String?,
    val manaCost: String?,
    val cmc: Double?,
    val colors: List<String>?,
    val colorIdentity: List<String>?,
    val type: String?,
    val types: List<String>?,
    val superTypes: List<String>?,
    val subTypes: List<String>?,
    val rarity: String?,
    val set: String?,
    val setName: String?,
    val text: String?,
    val flavor: String?,
    val artist: String?,
    val number: String?,
    val power: String?,
    val toughness: String?,
    val layout: String?,
    val multiverseId: Int?,
    val imageUrl: String?,
    val rulings: List<RulingModel>?,
    val foreignNames: List<ForeignNameModel>?,
    val printings: List<String>?,
    val originalText: String?,
    val originalType: String?,
    val id: String?
)
