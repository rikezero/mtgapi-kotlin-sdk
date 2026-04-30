package com.rikezero.mtgapi_kotlin_sdk.domain.model.types

import kotlinx.serialization.Serializable

@Serializable
data class TypesModel(
    val types: List<String>
)

@Serializable
data class SubtypesModel(
    val subTypes: List<String>
)

@Serializable
data class SuperTypesModel(
    val superTypes: List<String>
)