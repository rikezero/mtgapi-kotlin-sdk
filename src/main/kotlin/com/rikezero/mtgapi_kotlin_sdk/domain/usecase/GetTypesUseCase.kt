package com.rikezero.mtgapi_kotlin_sdk.domain.usecase

import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.TypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.base.MtgApiUseCase

class GetTypesUseCase(
    private val mtgApiRepository: MtgApiRepository
): MtgApiUseCase<Unit, TypesModel>() {
    override suspend fun execute(params: Unit): MtgApiResult<TypesModel> {
        return mtgApiRepository.getTypes()
    }
}