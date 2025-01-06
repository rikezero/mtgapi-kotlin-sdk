package com.rikezero.mtgapi_kotlin_sdk.networking.response.types

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TypesResponse(
    @SerializedName("types") val types: List<String>
)

@Serializable
data class SubtypesResponse(
    @SerializedName("subtypes") val subTypes: List<String>
)

@Serializable
data class SuperTypesResponse(
    @SerializedName("supertypes") val superTypes: List<String>
)