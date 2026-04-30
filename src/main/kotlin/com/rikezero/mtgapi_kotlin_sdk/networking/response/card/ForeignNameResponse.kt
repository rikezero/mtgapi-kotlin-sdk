package com.rikezero.mtgapi_kotlin_sdk.networking.response.card

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ForeignNameResponse(
    @SerializedName("name") val name: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("multiverseid") val multiverseid: Int?
)
