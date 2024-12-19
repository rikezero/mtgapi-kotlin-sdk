package com.rikezero.mtgapi_kotlin_sdk.domain.mappers

import CardResponse
import com.rikezero.mtgapi_kotlin_sdk.domain.model.FormatsModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.card.CardModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.card.ForeignNameModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.card.RulingModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.lists.CardListModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.lists.CardSetListModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.set.CardSetModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.SubtypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.SuperTypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.TypesModel
import com.rikezero.mtgapi_kotlin_sdk.networking.response.FormatsResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.card.ForeignNameResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.card.RulingResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.lists.CardListResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.lists.CardSetListResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.set.CardSetResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.types.SubtypesResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.types.SuperTypesResponse
import com.rikezero.mtgapi_kotlin_sdk.networking.response.types.TypesResponse

fun TypesResponse.toModel() = TypesModel(
    types = types
)

fun SubtypesResponse.toModel() = SubtypesModel(
    subTypes = subTypes
)

fun SuperTypesResponse.toModel() = SuperTypesModel(
    superTypes = superTypes
)

fun FormatsResponse.toModel() = FormatsModel(
    formats = formats
)

fun CardSetListResponse.toModel() = CardSetListModel(
    sets = this.sets?.map { it.toModel() }.orEmpty()
)

fun CardSetResponse.toModel() = CardSetModel(
    code = code,
    name = name,
    type = type,
    booster = booster,
    releaseDate = releaseDate,
    block = block,
    onlineOnly = onlineOnly
)

fun CardListResponse.toModel(): CardListModel {
    return CardListModel(
        cards = cards?.map { it.toModel() }.orEmpty()
    )
}

fun CardResponse.toModel(): CardModel {
    return CardModel(
        name = name,
        manaCost = manaCost,
        cmc = cmc,
        colors = colors,
        colorIdentity = colorIdentity,
        types = types,
        text = text,
        rulings = rulings?.map { it.toModel() },
        rarity = rarity,
        layout = layout,
        flavor = flavor,
        set = set,
        setName = setName,
        multiverseId = multiverseId,
        id = id,
        imageUrl = imageUrl,
        power = power,
        toughness = toughness,
        foreignNames = foreignNames?.map { it.toModel() },
        originalText = originalText,
        originalType = originalType,
        printings = printings,
        number = number,
        artist = artist,
        type = type,
        subTypes = subTypes,
        superTypes = superTypes
    )
}

fun RulingResponse.toModel(): RulingModel {
    return RulingModel(
        date = date,
        text = text
    )
}

fun ForeignNameResponse.toModel(): ForeignNameModel {
    return ForeignNameModel(
        name = name,
        language = language,
        multiverseid = multiverseid
    )
}