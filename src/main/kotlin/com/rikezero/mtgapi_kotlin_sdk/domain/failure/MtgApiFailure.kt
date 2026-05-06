package com.rikezero.mtgapi_kotlin_sdk.domain.failure

/**
 * Sealed hierarchy of all structured failure types produced by the MTG API SDK.
 *
 * Every error that propagates through [com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult]
 * is an instance of this class. Callers can exhaustively handle failures by switching on the
 * concrete subtype using `when`.
 *
 * @param message An optional human-readable description of the error.
 * @param cause The underlying exception that triggered this failure, if any.
 */
sealed class MtgApiFailure(
    message: String?,
    cause: Throwable?
): Throwable(message ?: cause?.message, cause) {

    private companion object {
         const val GENERIC_ERROR = "An error has occurred"
     }

    override val message: String?
        get() = super.message ?: GENERIC_ERROR

    /**
     * Produced when an error occurs that does not fit any other known failure category.
     *
     * Common scenarios include a `null` payload being returned where a non-null value was
     * required (e.g., via [com.rikezero.mtgapi_kotlin_sdk.domain.result.mapToNotNull]) or
     * an unexpected exception thrown during data mapping.
     *
     * @param cause The underlying exception.
     * @param message An optional human-readable description of the failure.
     */
    class UnknownFailure(cause: Throwable, message: String? = null) : MtgApiFailure(message, cause)

    /**
     * Produced when the network request to the MTG API fails or the server returns an error response.
     *
     * This failure carries a structured [Error] object with an API-specific error [Error.code]
     * and [Error.message], along with the optional HTTP status code.
     *
     * @property error The structured API error returned by the server.
     * @property httpCode The HTTP status code of the failed response (e.g., 404, 500), if available.
     * @param message An optional human-readable description of the failure.
     * @param cause The underlying exception, if any.
     */
    class NetworkingFailure(
        val error: Error,
        val httpCode: Int? = null,
        message: String? = null,
        cause: Throwable? = null
    ): MtgApiFailure(message, cause) {

        /**
         * Structured error detail returned by the MTG API server.
         *
         * @property code A machine-readable error code string identifying the error type.
         * @property message A human-readable description of the error.
         */
        class Error(
            val code: String,
            val message: String
        )
    }
}