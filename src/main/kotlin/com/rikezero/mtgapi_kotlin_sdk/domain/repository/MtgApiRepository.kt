package com.rikezero.mtgapi_kotlin_sdk.domain.repository

import com.rikezero.mtgapi_kotlin_sdk.domain.model.FormatsModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.card.CardModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.lists.CardListModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.lists.CardSetListModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.set.CardSetModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.SubtypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.SuperTypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.TypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.networking.response.MtgApiResponse

/**
 * Contract for all data operations performed against the MTG API.
 *
 * Implementations are responsible for fetching raw network responses, mapping them to domain
 * models, and returning the result wrapped in [MtgApiResult]. Callers should not interact
 * with the network layer directly — always go through this interface.
 */
interface MtgApiRepository {

    /**
     * Fetches a paginated, filtered list of cards.
     *
     * @param queries Key-value map of query parameters (e.g., name, set, rarity).
     * @return [MtgApiResult] wrapping a [CardListModel] on success, or a failure on error.
     */
    suspend fun getCards(queries: HashMap<String, String>): MtgApiResult<CardListModel>

    /**
     * Fetches a single card by its unique identifier.
     *
     * @param id The card's SHA1-based MTG API ID or Multiverse ID.
     * @return [MtgApiResult] wrapping a [CardModel] on success, or a failure on error.
     */
    suspend fun getCardById(id: String): MtgApiResult<CardModel>

    /**
     * Fetches a filtered list of Magic: The Gathering sets.
     *
     * @param queries Key-value map of query parameters (e.g., name, block).
     * @return [MtgApiResult] wrapping a [CardSetListModel] on success, or a failure on error.
     */
    suspend fun getSets(queries: HashMap<String, String>): MtgApiResult<CardSetListModel>

    /**
     * Fetches a single set by its set code.
     *
     * @param code The set code (e.g., "KTK" for Khans of Tarkir).
     * @return [MtgApiResult] wrapping a [CardSetModel] on success, or a failure on error.
     */
    suspend fun getSetByCode(code: String): MtgApiResult<CardSetModel>

    /**
     * Fetches all available card types (e.g., Creature, Instant, Sorcery).
     *
     * @return [MtgApiResult] wrapping a [TypesModel] on success, or a failure on error.
     */
    suspend fun getTypes(): MtgApiResult<TypesModel>

    /**
     * Fetches all available card subtypes (e.g., Human, Elf, Equipment).
     *
     * @return [MtgApiResult] wrapping a [SubtypesModel] on success, or a failure on error.
     */
    suspend fun getSubtypes(): MtgApiResult<SubtypesModel>

    /**
     * Fetches all available card supertypes (e.g., Basic, Legendary, Snow).
     *
     * @return [MtgApiResult] wrapping a [SuperTypesModel] on success, or a failure on error.
     */
    suspend fun getSupertypes(): MtgApiResult<SuperTypesModel>

    /**
     * Fetches all supported game formats (e.g., Standard, Legacy, Commander).
     *
     * @return [MtgApiResult] wrapping a [FormatsModel] on success, or a failure on error.
     */
    suspend fun getFormats(): MtgApiResult<FormatsModel>
}