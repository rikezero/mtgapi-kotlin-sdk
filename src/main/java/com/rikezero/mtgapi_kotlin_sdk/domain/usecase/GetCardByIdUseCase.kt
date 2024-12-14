package com.rikezero.mtgapi_kotlin_sdk.domain.usecase

import com.rikezero.mtgapi_kotlin_sdk.domain.model.card.CardModel
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult

/**
 * UseCase for searching a specific card.
 *
 * @property params Searches for a specific card by id or multiverseid
 *
 **/
class GetCardByIdUseCase(
    private val mtgApiRepository: MtgApiRepository
): MtgApiUseCase<String, CardModel>() {
    override suspend fun execute(params: String): MtgApiResult<CardModel> {
        return mtgApiRepository.getCardById(params)
    }
}