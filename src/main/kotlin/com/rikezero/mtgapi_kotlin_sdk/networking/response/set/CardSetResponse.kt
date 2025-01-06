package com.rikezero.mtgapi_kotlin_sdk.networking.response.set

import CardResponse
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CardSetResponse(
    @SerializedName("code") val code: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("booster") val booster: List<CardResponse>?,
    @SerializedName("releaseDate") val releaseDate: String?,
    @SerializedName("block") val block: String?,
    @SerializedName("onlineOnly") val onlineOnly: Boolean?
)
