package com.rikezero.mtgapi_kotlin_sdk.domain.usecase

import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.SuperTypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.base.MtgApiUseCase

/**
 * Use case for retrieving all Magic: The Gathering card supertypes (e.g., Basic, Legendary, Snow).
 *
 * Delegates to [MtgApiRepository.getSupertypes]. No input parameters are required.
 */
class GetSuperTypesUseCase(
    private val mtgApiRepository: MtgApiRepository
): MtgApiUseCase<Unit, SuperTypesModel>() {

    /**
     * Executes the use case by fetching all available card supertypes.
     *
     * @param params Unused — pass [Unit].
     * @return [MtgApiResult] wrapping a [SuperTypesModel] on success, or a failure on error.
     */
    override suspend fun execute(params: Unit): MtgApiResult<SuperTypesModel> {
        return mtgApiRepository.getSupertypes()
    }
}