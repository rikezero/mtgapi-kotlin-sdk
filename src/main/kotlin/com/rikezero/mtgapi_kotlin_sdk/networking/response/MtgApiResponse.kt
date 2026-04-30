package com.rikezero.mtgapi_kotlin_sdk.networking.response

/**
 * A sealed class that represents the outcome of an operation, typically one that involves fetching or
 * processing data. It can either be a successful result containing data, or an error result containing
 * an exception.
 *
 * Use [Success] to indicate a successful result containing data of type [T].
 * Use [Error] to indicate a failure with the given [exception].
 *
 * This class allows handling successes and errors in a type-safe manner, reducing the need for
 * null checks or additional flags.
 */
sealed class MtgApiResponse<out T> {

    /**
     * Represents a successful operation that produced a result of type [T].
     *
     * @param data The successfully obtained data.
     */
    data class Success<T>(val data: T) : MtgApiResponse<T>()

    /**
     * Represents a failed operation due to the given [exception].
     *
     * @param exception The exception that caused the operation to fail.
     */
    data class Error(val exception: Exception) : MtgApiResponse<Nothing>()

    /**
     * Invokes [action] if this is a [Success], providing the successfully obtained [data].
     *
     * @param action A lambda function that will be called with the success data if available.
     * @return Returns the original [MtgApiResponse] to allow for method chaining.
     *
     * Example usage:
     * ```
     * val response: MtgApiResponse<User> = fetchUser()
     * response.onSuccess { user ->
     *     println("Fetched user: $user")
     * }.onError { error ->
     *     println("Failed to fetch user: ${error.message}")
     * }
     * ```
     */
    inline fun onSuccess(action: (T) -> Unit): MtgApiResponse<T> {
        if (this is Success) action(data)
        return this
    }

    /**
     * Invokes [action] if this is an [Error], providing the [exception] that caused the failure.
     *
     * @param action A lambda function that will be called with the encountered exception if available.
     * @return Returns the original [MtgApiResponse] to allow for method chaining.
     */
    inline fun onError(action: (Exception) -> Unit): MtgApiResponse<T> {
        if (this is Error) action(exception)
        return this
    }
}