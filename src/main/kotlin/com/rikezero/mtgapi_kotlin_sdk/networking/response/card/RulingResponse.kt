package com.rikezero.mtgapi_kotlin_sdk.networking.response.card

import com.google.gson.annotations.SerializedName

data class RulingResponse(
    @SerializedName("date") val date: String?,
    @SerializedName("text") val text: String?
)
