package com.rikezero.mtgapi_kotlin_sdk.networking.impl

import CardResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.MtgApiNetworking
import com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter.MtgApiNetworkAdapter
import com.rikezero.mtgapi_kotlin_sdk.networking.response.FormatsResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.MtgApiResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.card.CardSingleResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.lists.CardListResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.lists.CardSetListResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.set.CardSetResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.set.CardSetSingleResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.types.SubtypesResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.types.SuperTypesResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.types.TypesResponse

class MtgApiNetworkingImpl(
    private val networkAdapter: MtgApiNetworkAdapter
): MtgApiNetworking {

    private companion object {
        const val CARDS_ENDPOINT = "v1/cards"
        const val SETS_ENDPOINT = "v1/sets"
        const val TYPES_ENDPOINT = "v1/types"
        const val SUBTYPES_ENDPOINT = "v1/subtypes"
        const val SUPERTYPES_ENDPOINT = "v1/supertypes"
        const val FORMATS_ENDPOINT = "v1/formats"
    }

    override suspend fun getCards(queries: HashMap<String, String>): MtgApiResponse<CardListResponse?> {
        return networkAdapter.get(
            url = CARDS_ENDPOINT,
            queryParams = queries,
            responseClass = CardListResponse::class
        )
    }

    override suspend fun getCardById(id: String): MtgApiResponse<CardResponse?> {
        val response = networkAdapter.get(
            url = "${CARDS_ENDPOINT}/${id}",
            responseClass = CardSingleResponse::class
        )
        return when (response) {
            is MtgApiResponse.Success -> MtgApiResponse.Success(response.data?.card)
            is MtgApiResponse.Error -> response
        }
    }

    override suspend fun getSets(queries: HashMap<String, String>): MtgApiResponse<CardSetListResponse?> {
        return networkAdapter.get(
            queryParams = queries,
            url = SETS_ENDPOINT,
            responseClass = CardSetListResponse::class
        )
    }

    override suspend fun getSetByCode(code: String): MtgApiResponse<CardSetResponse?> {
        val response = networkAdapter.get(
            url = "${SETS_ENDPOINT}/${code}",
            responseClass = CardSetSingleResponse::class
        )
        return when (response) {
            is MtgApiResponse.Success -> MtgApiResponse.Success(response.data?.set)
            is MtgApiResponse.Error -> response
        }
    }

    override suspend fun getTypes(): MtgApiResponse<TypesResponse?> {
        return networkAdapter.get(
            url = TYPES_ENDPOINT,
            responseClass = TypesResponse::class
        )
    }

    override suspend fun getSubtypes(): MtgApiResponse<SubtypesResponse?> {
        return networkAdapter.get(
            url = SUBTYPES_ENDPOINT,
            responseClass = SubtypesResponse::class
        )
    }

    override suspend fun getSupertypes(): MtgApiResponse<SuperTypesResponse?> {
        return networkAdapter.get(
            url = SUPERTYPES_ENDPOINT,
            responseClass = SuperTypesResponse::class
        )
    }

    override suspend fun getFormats(): MtgApiResponse<FormatsResponse?> {
        return networkAdapter.get(
            url = FORMATS_ENDPOINT,
            responseClass = FormatsResponse::class
        )
    }
}