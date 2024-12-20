package com.rikezero.mtgapi_kotlin_sdk.domain.usecase

import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.SuperTypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.base.MtgApiUseCase

class GetSuperTypesUseCase(
    private val mtgApiRepository: MtgApiRepository
): MtgApiUseCase<Unit, SuperTypesModel>() {
    override suspend fun execute(params: Unit): MtgApiResult<SuperTypesModel> {
        return mtgApiRepository.getSupertypes()
    }
}