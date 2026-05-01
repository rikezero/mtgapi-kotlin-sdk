package com.rikezero.mtgapi_kotlin_sdk.networking.response.card

import CardResponse
import com.google.gson.annotations.SerializedName

data class CardSingleResponse(
    @SerializedName("card") val card: CardResponse?
)
