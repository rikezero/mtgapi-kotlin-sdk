package com.rikezero.mtgapi_kotlin_sdk.networking.response.types

import com.google.gson.annotations.SerializedName

data class TypesResponse(
    @SerializedName("types") val types: List<String>
)

data class SubtypesResponse(
    @SerializedName("subtypes") val subTypes: List<String>
)

data class SuperTypesResponse(
    @SerializedName("supertypes") val superTypes: List<String>
)