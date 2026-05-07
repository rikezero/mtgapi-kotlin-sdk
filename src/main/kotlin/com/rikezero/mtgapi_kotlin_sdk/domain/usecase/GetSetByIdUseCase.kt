package com.rikezero.mtgapi_kotlin_sdk.domain.usecase

import com.rikezero.mtgapi_kotlin_sdk.domain.model.set.CardSetModel
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.base.MtgApiUseCase

/**
 * Use case for retrieving a single Magic: The Gathering set by its set code.
 *
 * Delegates to [MtgApiRepository.getSetByCode] with the provided set code string.
 */
class GetSetByIdUseCase(
    private val mtgApiRepository: MtgApiRepository
): MtgApiUseCase<String,CardSetModel>() {

    /**
     * Executes the use case by fetching the set matching the given set code.
     *
     * @param params The set code (e.g., "KTK" for Khans of Tarkir) to look up.
     * @return [MtgApiResult] wrapping a [CardSetModel] on success, or a failure on error.
     */
    override suspend fun execute(params: String): MtgApiResult<CardSetModel> {
        return mtgApiRepository.getSetByCode(params)
    }
}