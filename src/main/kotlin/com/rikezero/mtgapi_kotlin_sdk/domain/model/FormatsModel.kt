package com.rikezero.mtgapi_kotlin_sdk.domain.model

import kotlinx.serialization.Serializable

/**
 * Domain model containing all supported Magic: The Gathering game formats.
 *
 * Game formats define the rules and card pool used in competitive and casual play.
 * Examples include Standard, Modern, Legacy, Vintage, Commander, and Pioneer.
 *
 * @property formats The list of format names supported by the MTG API.
 */
@Serializable
data class FormatsModel(
    val formats: List<String>
)