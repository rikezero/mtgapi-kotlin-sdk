package com.rikezero.mtgapi_kotlin_sdk.domain.usecase

import com.rikezero.mtgapi_kotlin_sdk.domain.model.FormatsModel
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.base.MtgApiUseCase

class GetFormatsUseCase(
    private val mtgApiRepository: MtgApiRepository
): MtgApiUseCase<Unit, FormatsModel>() {
    override suspend fun execute(params: Unit): MtgApiResult<FormatsModel> {
        return mtgApiRepository.getFormats()
    }
}