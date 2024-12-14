package com.rikezero.mtgapi_kotlin_sdk.domain.repository

import com.rikezero.mtgapi_kotlin_sdk.domain.model.FormatsModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.card.CardModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.lists.CardListModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.lists.CardSetListModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.set.CardSetModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.SubtypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.SuperTypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.TypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.networking.response.MtgApiResponse

interface MtgApiRepository {

    suspend fun getCards(queries: HashMap<String, String>): MtgApiResult<CardListModel>

    suspend fun getCardById(id: String): MtgApiResult<CardModel>

    suspend fun getSets(): MtgApiResult<CardSetListModel>

    suspend fun getSetByCode(code: String): MtgApiResult<CardSetModel>

    suspend fun getTypes(): MtgApiResult<TypesModel>

    suspend fun getSubtypes(): MtgApiResult<SubtypesModel>

    suspend fun getSupertypes(): MtgApiResult<SuperTypesModel>

    suspend fun getFormats(): MtgApiResult<FormatsModel>
}