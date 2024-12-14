package com.rikezero.mtgapi_kotlin_sdk.networking.response.set

import com.google.gson.annotations.SerializedName

data class CardSetResponse(
    @SerializedName("code") val code: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("booster") val booster: List<Any>?,
    @SerializedName("releaseDate") val releaseDate: String?,
    @SerializedName("block") val block: String?,
    @SerializedName("onlineOnly") val onlineOnly: Boolean?
)
