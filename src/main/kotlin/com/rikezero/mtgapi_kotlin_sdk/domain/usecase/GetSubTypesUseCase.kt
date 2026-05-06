package com.rikezero.mtgapi_kotlin_sdk.domain.usecase

import com.rikezero.mtgapi_kotlin_sdk.domain.model.types.SubtypesModel
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.base.MtgApiUseCase

/**
 * Use case for retrieving all Magic: The Gathering card subtypes (e.g., Human, Elf, Equipment).
 *
 * Delegates to [MtgApiRepository.getSubtypes]. No input parameters are required.
 */
class GetSubTypesUseCase(
    private val mtgApiRepository: MtgApiRepository
): MtgApiUseCase<Unit, SubtypesModel>() {

    /**
     * Executes the use case by fetching all available card subtypes.
     *
     * @param params Unused — pass [Unit].
     * @return [MtgApiResult] wrapping a [SubtypesModel] on success, or a failure on error.
     */
    override suspend fun execute(params: Unit): MtgApiResult<SubtypesModel> {
        return mtgApiRepository.getSubtypes()
    }
}