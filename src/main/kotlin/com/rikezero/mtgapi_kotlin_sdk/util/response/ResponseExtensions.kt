package com.rikezero.mtgapi_kotlin_sdk.util.response

import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult
import com.rikezero.mtgapi_kotlin_sdk.networking.response.MtgApiResponse

fun <T> MtgApiResponse<T>.toResult(): MtgApiResult<T> = when (this) {
    is MtgApiResponse.Success -> MtgApiResult.success(data)
    is MtgApiResponse.Error -> MtgApiResult.failure(exception)
}

inline fun <T> result(
    block: () -> MtgApiResponse<T>
): MtgApiResult<T> = runCatching {
    block().toResult()
}.getOrElse { error ->
    MtgApiResult.failure(error)
}
