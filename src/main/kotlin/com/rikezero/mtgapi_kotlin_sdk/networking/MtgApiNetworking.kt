package com.rikezero.mtgapi_kotlin_sdk.networking

import CardResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.FormatsResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.MtgApiError
import com.rikezero.mtgapi_kotlin_sdk.networking.response.MtgApiResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.lists.CardListResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.lists.CardSetListResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.set.CardSetResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.types.SubtypesResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.types.SuperTypesResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.types.TypesResponse
import kotlin.jvm.Throws

/**
 * Low-level networking contract for the MTG API.
 *
 * Each method maps to a specific MTG API endpoint and returns the raw response type wrapped
 * in [MtgApiResponse]. All methods may throw [MtgApiError] on network or server failures.
 * Callers in the domain layer should not use this interface directly — use the repository instead.
 */
interface MtgApiNetworking {

    /**
     * Fetches a list of cards matching the provided query parameters.
     *
     * @param queries Key-value map of query parameters (e.g., name, set, rarity).
     * @return [MtgApiResponse] wrapping a nullable [CardListResponse].
     * @throws MtgApiError if the network request fails or the server returns an error.
     */
    @Throws(MtgApiError::class)
    suspend fun getCards(queries: HashMap<String,String>): MtgApiResponse<CardListResponse?>

    /**
     * Fetches a single card matching the provided identifier.
     *
     * @param id The card's MTG API ID or Multiverse ID.
     * @return [MtgApiResponse] wrapping a nullable [CardResponse].
     * @throws MtgApiError if the network request fails or the server returns an error.
     */
    @Throws(MtgApiError::class)
    suspend fun getCardById(id: String): MtgApiResponse<CardResponse?>

    /**
     * Fetches a list of sets matching the provided query parameters.
     *
     * @param queries Key-value map of query parameters (e.g., name, block).
     * @return [MtgApiResponse] wrapping a nullable [CardSetListResponse].
     * @throws MtgApiError if the network request fails or the server returns an error.
     */
    @Throws(MtgApiError::class)
    suspend fun getSets(queries: HashMap<String, String>): MtgApiResponse<CardSetListResponse?>

    /**
     * Fetches a single set matching the provided set code.
     *
     * @param code The set code (e.g., "KTK" for Khans of Tarkir).
     * @return [MtgApiResponse] wrapping a nullable [CardSetResponse].
     * @throws MtgApiError if the network request fails or the server returns an error.
     */
    @Throws(MtgApiError::class)
    suspend fun getSetByCode(code: String): MtgApiResponse<CardSetResponse?>

    /**
     * Fetches all available card types from the MTG API.
     *
     * @return [MtgApiResponse] wrapping a nullable [TypesResponse].
     * @throws MtgApiError if the network request fails or the server returns an error.
     */
    @Throws(MtgApiError::class)
    suspend fun getTypes(): MtgApiResponse<TypesResponse?>

    /**
     * Fetches all available card subtypes from the MTG API.
     *
     * @return [MtgApiResponse] wrapping a nullable [SubtypesResponse].
     * @throws MtgApiError if the network request fails or the server returns an error.
     */
    @Throws(MtgApiError::class)
    suspend fun getSubtypes(): MtgApiResponse<SubtypesResponse?>

    /**
     * Fetches all available card supertypes from the MTG API.
     *
     * @return [MtgApiResponse] wrapping a nullable [SuperTypesResponse].
     * @throws MtgApiError if the network request fails or the server returns an error.
     */
    @Throws(MtgApiError::class)
    suspend fun getSupertypes(): MtgApiResponse<SuperTypesResponse?>

    /**
     * Fetches all supported game formats from the MTG API.
     *
     * @return [MtgApiResponse] wrapping a nullable [FormatsResponse].
     * @throws MtgApiError if the network request fails or the server returns an error.
     */
    @Throws(MtgApiError::class)
    suspend fun getFormats(): MtgApiResponse<FormatsResponse?>
}