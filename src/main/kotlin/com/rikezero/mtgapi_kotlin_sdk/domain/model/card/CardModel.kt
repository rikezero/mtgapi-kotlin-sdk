package com.rikezero.mtgapi_kotlin_sdk.domain.model.card

import kotlinx.serialization.Serializable

@Serializable
data class CardModel(
    val name: String?,
    val manaCost: String?,
    val cmc: Double?,
    val colors: List<String>?,
    val colorIdentity: List<String>?,
    val type: String?,
    val types: List<String>?,
    val superTypes: List<String>?,
    val subTypes: List<String>?,
    val rarity: String?,
    val set: String?,
    val setName: String?,
    val text: String?,
    val flavor: String?,
    val artist: String?,
    val number: String?,
    val power: String?,
    val toughness: String?,
    val layout: String?,
    val multiverseId: Int?,
    val imageUrl: String?,
    val rulings: List<RulingModel>?,
    val foreignNames: List<ForeignNameModel>?,
    val printings: List<String>?,
    val originalText: String?,
    val originalType: String?,
    val id: String?
)
