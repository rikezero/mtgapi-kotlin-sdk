package com.rikezero.mtgapi_kotlin_sdk.networking.service

import com.rikezero.mtgapi_kotlin_sdk.networking.response.FormatsResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.lists.CardListResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.lists.CardSetListResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.types.SubtypesResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.types.SuperTypesResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.types.TypesResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface MtgApiService {

    @GET("v1/cards")
    suspend fun getCards(
        @Query("name") name: String? = null,
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null
    ): CardListResponse

    @GET("v1/cards/{id}")
    suspend fun getCardById(
        @Path("id") id: String
    ): CardListResponse

    @GET("v1/sets")
    suspend fun getSets(): CardSetListResponse

    @GET("v1/sets/{code}")
    suspend fun getSetByCode(
        @Path("code") code: String
    ): CardSetListResponse

    @GET("v1/types")
    suspend fun getTypes(): TypesResponse

    @GET("v1/subtypes")
    suspend fun getSubtypes(): SubtypesResponse

    @GET("v1/supertypes")
    suspend fun getSupertypes(): SuperTypesResponse

    @GET("v1/formats")
    suspend fun getFormats(): FormatsResponse

    @GET
    suspend fun get(
        @Url url: String,
        @HeaderMap headers: Map<String, String>,
        @QueryMap queryParams: Map<String, String>
    ): ResponseBody
}
