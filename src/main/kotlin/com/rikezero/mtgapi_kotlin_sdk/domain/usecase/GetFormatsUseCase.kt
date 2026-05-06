package com.rikezero.mtgapi_kotlin_sdk.domain.usecase

import com.rikezero.mtgapi_kotlin_sdk.domain.model.FormatsModel
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.base.MtgApiUseCase

/**
 * Use case for retrieving all supported Magic: The Gathering game formats (e.g., Standard, Legacy, Commander).
 *
 * Delegates to [MtgApiRepository.getFormats]. No input parameters are required.
 */
class GetFormatsUseCase(
    private val mtgApiRepository: MtgApiRepository
): MtgApiUseCase<Unit, FormatsModel>() {

    /**
     * Executes the use case by fetching all available game formats.
     *
     * @param params Unused — pass [Unit].
     * @return [MtgApiResult] wrapping a [FormatsModel] on success, or a failure on error.
     */
    override suspend fun execute(params: Unit): MtgApiResult<FormatsModel> {
        return mtgApiRepository.getFormats()
    }
}