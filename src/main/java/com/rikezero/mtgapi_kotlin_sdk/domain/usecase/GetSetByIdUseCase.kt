package com.rikezero.mtgapi_kotlin_sdk.domain.usecase

import com.rikezero.mtgapi_kotlin_sdk.domain.model.set.CardSetModel
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.base.MtgApiUseCase

class GetSetByIdUseCase(
    private val mtgApiRepository: MtgApiRepository
): MtgApiUseCase<String,CardSetModel>() {
    override suspend fun execute(params: String): MtgApiResult<CardSetModel> {
        return mtgApiRepository.getSetByCode(params)
    }
}