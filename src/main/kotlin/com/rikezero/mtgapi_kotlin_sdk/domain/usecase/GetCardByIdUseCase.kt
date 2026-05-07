package com.rikezero.mtgapi_kotlin_sdk.domain.usecase

import com.rikezero.mtgapi_kotlin_sdk.domain.model.card.CardModel
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.base.MtgApiUseCase

/**
 * Use case for retrieving a single Magic: The Gathering card by its unique identifier.
 *
 * Delegates to [MtgApiRepository.getCardById] with the provided card ID.
 * The ID may be either the MTG API's own SHA1-based card ID or a Multiverse ID.
 */
class GetCardByIdUseCase(
    private val mtgApiRepository: MtgApiRepository
): MtgApiUseCase<String, CardModel>() {

    /**
     * Executes the use case by fetching a single card matching the given ID.
     *
     * @param params The card ID (SHA1-based MTG API ID or Multiverse ID) to look up.
     * @return [MtgApiResult] wrapping a [CardModel] on success, or a failure on error.
     */
    override suspend fun execute(params: String): MtgApiResult<CardModel> {
        return mtgApiRepository.getCardById(params)
    }
}