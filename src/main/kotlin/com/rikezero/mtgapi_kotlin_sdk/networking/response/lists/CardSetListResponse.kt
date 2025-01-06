package com.rikezero.mtgapi_kotlin_sdk.networking.response.lists

import com.google.gson.annotations.SerializedName
import com.rikezero.mtgapi_kotlin_sdk.networking.response.set.CardSetResponse
import kotlinx.serialization.Serializable

@Serializable
data class CardSetListResponse(
    @SerializedName("sets") val sets: List<CardSetResponse>?
)
