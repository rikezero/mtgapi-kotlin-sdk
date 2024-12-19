package com.rikezero.mtgapi_kotlin_sdk.domain.usecase

import com.rikezero.mtgapi_kotlin_sdk.domain.model.lists.CardListModel
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetCardsUseCase.GetCardsParams
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.base.MtgApiUseCase

class  GetCardsUseCase(
    private val mtgApiRepository: MtgApiRepository
): MtgApiUseCase<GetCardsParams, CardListModel>() {

    /**
     * Data class containing all parameters for filtering cards.
     *
     * @property name The card name. For split, double-faced, and flip cards, just the name of one side of the card. Each 'sub-card' has its own record.
     * @property layout The card layout. Possible values: normal, split, flip, double-faced, token, plane, scheme, phenomenon, leveler, vanguard, aftermath.
     * @property cmc Converted mana cost. Always a number.
     * @property colors The card colors. Usually derived from the casting cost, but some cards are exceptions (e.g., Ghostfire or the back of dual-sided cards).
     * @property colorIdentity The card’s color identity, by color code. For example, [“Red”, “Blue”] becomes [“R”, “U”]. A card’s color identity includes colors from the card’s rules text.
     * @property type The card type as seen on a printed card today. The dash is a UTF-8 'long dash' as per MTG rules.
     * @property supertypes The supertypes of the card, which appear to the far left of the card type. Example values: Basic, Legendary, Snow, World, Ongoing.
     * @property types The types of the card, appearing to the left of the dash in a card type. Example values: Instant, Sorcery, Artifact, Creature, Enchantment, Land, Planeswalker.
     * @property subtypes The subtypes of the card, appearing to the right of the dash in a card type. Example values: Trap, Arcane, Equipment, Aura, Human, Rat, Squirrel, etc.
     * @property rarity The rarity of the card. Examples: Common, Uncommon, Rare, Mythic Rare, Special, Basic Land.
     * @property set The set the card belongs to, represented by the set code.
     * @property setName The name of the set the card belongs to.
     * @property text The Oracle text of the card. May contain mana symbols and other symbols.
     * @property flavor The flavor text of the card.
     * @property artist The artist of the card. This may differ from the card's print due to corrections for misprints.
     * @property number The card number, printed at the bottom-center of the card. This is a string, as some cards have letters in their numbers.
     * @property power The power of the card, present only for creatures. This is a string because some cards have powers like “1+*”.
     * @property toughness The toughness of the card, present only for creatures. This is a string because some cards have toughness like “1+*”.
     * @property loyalty The loyalty of the card, present only for planeswalkers.
     * @property language The language in which the card is printed. Use this with the `name` parameter when searching by foreign name.
     * @property gameFormat The game format, such as Commander, Standard, Legacy, etc. When used, legality defaults to Legal unless specified.
     * @property legality The legality of the card for a given format (e.g., Legal, Banned, Restricted).
     * @property page The page number of data to request.
     * @property pageSize The amount of data to return in a single request. Defaults to and is capped at 100.
     * @property orderBy The field to order the response results by.
     * @property random Whether to fetch a random set of cards. Controlled by `pageSize`.
     * @property contains Filter cards based on whether they have a specific field available (e.g., `imageUrl`).
     * @property id A unique ID for this card, generated as an SHA1 hash of `setCode + cardName + cardImageName`.
     * @property multiverseid The multiverse ID of the card on Wizards’ Gatherer webpage. Cards from sets not on Gatherer will not have a multiverse ID.
     *                  Sets not on Gatherer include: ATH, ITP, DKM, RQS, DPA, and sets with a four-letter code starting with a lowercase 'p'.
     */
    data class GetCardsParams(
        val name: String? = null,
        val layout: String? = null,
        val cmc: Double? = null,
        val colors: List<String>? = null,
        val colorIdentity: List<String>? = null,
        val type: String? = null,
        val supertypes: List<String>? = null,
        val types: List<String>? = null,
        val subtypes: List<String>? = null,
        val rarity: String? = null,
        val set: String? = null,
        val setName: String? = null,
        val text: String? = null,
        val flavor: String? = null,
        val artist: String? = null,
        val number: String? = null,
        val power: String? = null,
        val toughness: String? = null,
        val loyalty: String? = null,
        val language: String? = null,
        val gameFormat: String? = null,
        val legality: String? = null,
        val page: Int? = null,
        val pageSize: Int? = null,
        val orderBy: String? = null,
        val random: Boolean? = null,
        val contains: String? = null,
        val id: String? = null,
        val multiverseid: String? = null
    ) {
        /**
         * Converts this object into a HashMap<String, String>.
         *
         * Only non-null properties are included in the map.
         */
        fun toHashMap(): HashMap<String, String> {
            val map = hashMapOf<String, String>()

            this::class.members.filter { it.name != "toHashMap" }
                .forEach { property ->
                    val value = property.call(this)
                    if (value != null) {
                        map[property.name] = when (value) {
                            is List<*> -> value.joinToString(",")
                            else -> value.toString()
                        }
                    }
                }

            return map
        }
    }

    override suspend fun execute(params: GetCardsParams): MtgApiResult<CardListModel> {
        return mtgApiRepository.getCards(params.toHashMap())
    }
}