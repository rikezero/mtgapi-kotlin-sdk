package com.rikezero.mtgapi_kotlin_sdk.networking.response.lists

import CardResponse
import com.google.gson.annotations.SerializedName

data class CardListResponse(
    @SerializedName("cards") val cards: List<CardResponse>?
)