package com.rikezero.mtgapi_kotlin_sdk.domain.usecase

import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.TypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.base.MtgApiUseCase

/**
 * Use case for retrieving all Magic: The Gathering card types (e.g., Creature, Instant, Sorcery).
 *
 * Delegates to [MtgApiRepository.getTypes]. No input parameters are required.
 */
class GetTypesUseCase(
    private val mtgApiRepository: MtgApiRepository
): MtgApiUseCase<Unit, TypesModel>() {

    /**
     * Executes the use case by fetching all available card types.
     *
     * @param params Unused — pass [Unit].
     * @return [MtgApiResult] wrapping a [TypesModel] on success, or a failure on error.
     */
    override suspend fun execute(params: Unit): MtgApiResult<TypesModel> {
        return mtgApiRepository.getTypes()
    }
}