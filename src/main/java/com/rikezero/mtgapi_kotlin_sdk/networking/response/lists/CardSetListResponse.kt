package com.rikezero.mtgapi_kotlin_sdk.networking.response.lists

import com.google.gson.annotations.SerializedName
import com.rikezero.mtgapi_kotlin_sdk.networking.response.set.CardSetResponse

data class CardSetListResponse(
    @SerializedName("sets") val sets: List<CardSetResponse>?
)
