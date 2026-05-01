package com.rikezero.mtgapi_kotlin_sdk.networking.response.set

import com.google.gson.annotations.SerializedName

data class CardSetSingleResponse(
    @SerializedName("set") val set: CardSetResponse?
)
