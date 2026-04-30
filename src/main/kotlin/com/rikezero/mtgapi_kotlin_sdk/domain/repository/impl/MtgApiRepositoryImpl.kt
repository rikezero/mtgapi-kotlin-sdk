package com.rikezero.mtgapi_kotlin_sdk.domain.repository.impl

import com.rikezero.mtgapi_kotlin_sdk.domain.mappers.toModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.FormatsModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.card.CardModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.lists.CardListModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.lists.CardSetListModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.set.CardSetModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.SubtypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.SuperTypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.TypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.domain.result.mapToNotNull
import com.rikezero.mtgapi_kotlin_sdk.networking.MtgApiNetworking
import com.rikezero.mtgapi_kotlin_sdk.util.response.result

class MtgApiRepositoryImpl(
    private val mtgApiNetworking: MtgApiNetworking
): MtgApiRepository {
    override suspend fun getCards(queries: HashMap<String, String>): MtgApiResult<CardListModel> {
        return result {
            mtgApiNetworking.getCards(queries = queries)
        }.mapToNotNull { cardList ->
            cardList.toModel()
        }
    }

    override suspend fun getCardById(id: String): MtgApiResult<CardModel> {
        return result {
            mtgApiNetworking.getCardById(id = id)
        }.mapToNotNull { card ->
            card.toModel()
        }
    }

    override suspend fun getSets(queries: HashMap<String, String>): MtgApiResult<CardSetListModel> {
        return result {
            mtgApiNetworking.getSets(queries = queries)
        }.mapToNotNull { sets ->
            sets.toModel()
        }
    }

    override suspend fun getSetByCode(code: String): MtgApiResult<CardSetModel> {
        return result {
            mtgApiNetworking.getSetByCode(code = code)
        }.mapToNotNull { set ->
            set.toModel()
        }
    }

    override suspend fun getTypes(): MtgApiResult<TypesModel> {
        return result {
            mtgApiNetworking.getTypes()
        }.mapToNotNull { types ->
            types.toModel()
        }
    }

    override suspend fun getSubtypes(): MtgApiResult<SubtypesModel> {
        return result {
            mtgApiNetworking.getSubtypes()
        }.mapToNotNull { types ->
            types.toModel()
        }
    }

    override suspend fun getSupertypes(): MtgApiResult<SuperTypesModel> {
        return result {
            mtgApiNetworking.getSupertypes()
        }.mapToNotNull { types ->
            types.toModel()
        }
    }

    override suspend fun getFormats(): MtgApiResult<FormatsModel> {
        return result {
            mtgApiNetworking.getFormats()
        }.mapToNotNull { formats ->
            formats.toModel()
        }
    }
}