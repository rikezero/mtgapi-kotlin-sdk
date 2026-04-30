package com.rikezero.mtgapi_kotlin_sdk.networking.response

import com.google.gson.annotations.SerializedName

class MtgApiError @JvmOverloads constructor(
    @SerializedName("status") val status: String?,
    @SerializedName("error") val error: String?,
    cause: Throwable? = null
): Throwable(message= error,cause = cause)
