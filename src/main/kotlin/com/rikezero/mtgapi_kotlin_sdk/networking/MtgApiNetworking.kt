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

interface MtgApiNetworking {
    @Throws(MtgApiError::class)
    suspend fun getCards(queries: HashMap<String,String>): MtgApiResponse<CardListResponse?>

    @Throws(MtgApiError::class)
    suspend fun getCardById(id: String): MtgApiResponse<CardResponse?>

    @Throws(MtgApiError::class)
    suspend fun getSets(queries: HashMap<String, String>): MtgApiResponse<CardSetListResponse?>

    @Throws(MtgApiError::class)
    suspend fun getSetByCode(code: String): MtgApiResponse<CardSetResponse?>

    @Throws(MtgApiError::class)
    suspend fun getTypes(): MtgApiResponse<TypesResponse?>

    @Throws(MtgApiError::class)
    suspend fun getSubtypes(): MtgApiResponse<SubtypesResponse?>

    @Throws(MtgApiError::class)
    suspend fun getSupertypes(): MtgApiResponse<SuperTypesResponse?>

    @Throws(MtgApiError::class)
    suspend fun getFormats(): MtgApiResponse<FormatsResponse?>
}