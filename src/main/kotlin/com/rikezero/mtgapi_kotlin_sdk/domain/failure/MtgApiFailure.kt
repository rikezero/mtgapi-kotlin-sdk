package com.rikezero.mtgapi_kotlin_sdk.domain.failure

sealed class MtgApiFailure(
    message: String?,
    cause: Throwable?
): Throwable(message ?: cause?.message, cause) {

    private companion object {
         const val GENERIC_ERROR = "An error has occurred"
     }

    override val message: String?
        get() = super.message ?: GENERIC_ERROR

    class UnknownFailure(cause: Throwable, message: String? = null) : MtgApiFailure(message, cause)

    class NetworkingFailure(
        val error: Error,
        val httpCode: Int? = null,
        message: String? = null,
        cause: Throwable? = null
    ): MtgApiFailure(message, cause) {

        class Error(
            val code: String,
            val message: String
        )
    }
}