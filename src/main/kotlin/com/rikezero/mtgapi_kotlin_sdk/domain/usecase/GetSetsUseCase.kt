package com.rikezero.mtgapi_kotlin_sdk.domain.usecase

import com.rikezero.mtgapi_kotlin_sdk.domain.model.lists.CardSetListModel
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetSetsUseCase.SetParameters
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.base.MtgApiUseCase

class GetSetsUseCase(
    private val mtgApiRepository: MtgApiRepository
): MtgApiUseCase<SetParameters, CardSetListModel>() {
    /**
     * Represents the parameters for a Magic: The Gathering set query.
     *
     * @property name The name of the set.
     * @property block The block the set is in.
     */
    data class SetParameters(
        val name: String? = null,
        val block: String? = null
    ) {
        /**
         * Converts this object into a HashMap<String, String>.
         *
         * Only non-null properties are included in the map.
         */
        fun toHashMap(): HashMap<String, String> {
            val map = hashMapOf<String, String>()

            this::class.members.filter { it.name != "toHashMap" }
                .forEach { property ->
                    val value = property.call(this)
                    if (value != null) {
                        map[property.name] = when (value) {
                            is List<*> -> value.joinToString(",")
                            else -> value.toString()
                        }
                    }
                }

            return map
        }
    }

    override suspend fun execute(params: SetParameters): MtgApiResult<CardSetListModel> {
        return mtgApiRepository.getSets(params.toHashMap())
    }

}