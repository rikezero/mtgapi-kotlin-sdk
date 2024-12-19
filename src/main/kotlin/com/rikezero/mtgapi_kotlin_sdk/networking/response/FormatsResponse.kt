package com.rikezero.mtgapi_kotlin_sdk.networking.response

import com.google.gson.annotations.SerializedName

data class FormatsResponse(
    @SerializedName("formats") val formats: List<String>
)