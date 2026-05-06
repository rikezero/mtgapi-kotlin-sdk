package com.rikezero.mtgapi_kotlin_sdk.domain.result

import com.rikezero.mtgapi_kotlin_sdk.domain.failure.MtgApiFailure

/**
 * A discriminated result container used throughout the MTG API SDK.
 *
 * An instance either holds a successful value of type [R] or a [Throwable] representing failure.
 * Use [isSuccess] / [isFailure] to branch, [getOrNull] to extract the value, and [exceptionOrNull]
 * to extract the cause. The extension functions [onSuccess], [onFailure], [map], and [mapToNotNull]
 * provide a fluent API for chaining result handling.
 *
 * Create instances via the companion object factory methods:
 * - [MtgApiResult.success] — wraps a successful value.
 * - [MtgApiResult.failure] — wraps a [Throwable] as a failure.
 *
 * @param R The type of the success value.
 */
open class MtgApiResult<out R> @PublishedApi internal constructor(
    @PublishedApi
    internal val value: Any? = null
){
    /** `true` when this result holds a successful value. */
    val isSuccess: Boolean = value !is Throwable

    /** `true` when this result holds a failure [Throwable]. */
    val isFailure: Boolean = value is Throwable

    /**
     * Returns the encapsulated success value, or `null` if this is a failure.
     */
    @Suppress("UNCHECKED_CAST")
    fun getOrNull(): R? = when {
        isFailure -> null
        else -> value as R
    }

    /**
     * Returns the encapsulated [Throwable] if this is a failure, or `null` on success.
     */
    fun exceptionOrNull(): Throwable? = when {
        isFailure -> value as Throwable?
        else -> null
    }

    companion object{
        /**
         * Creates a successful [MtgApiResult] wrapping [value].
         *
         * @param value The successful result value.
         */
        fun <T> success(value: T): MtgApiResult<T> = MtgApiResult(value)

        /**
         * Creates a failed [MtgApiResult] wrapping [throwable].
         *
         * @param throwable The exception describing why the operation failed.
         */
        fun <T> failure(throwable: Throwable): MtgApiResult<T> = MtgApiResult(throwable)
    }
}

/**
 * Transforms the success value of this result using [transform], leaving failure results unchanged.
 *
 * @param T The type of the original success value.
 * @param R The type of the transformed success value.
 * @param transform A function applied to the success value to produce a new value of type [R].
 * @return A new [MtgApiResult] containing the transformed value on success, or the original failure.
 */
@Suppress("UNCHECKED_CAST")
inline fun <T, R> MtgApiResult<T>.map(
    transform: (value: T) -> R
): MtgApiResult<R> = when {
    isSuccess -> MtgApiResult.success(transform(value as T))
    else -> MtgApiResult.failure(value as Throwable)
}

/**
 * Transforms the nullable success value of this result using [transform], treating a `null` value
 * as a failure.
 *
 * If the success value is `null`, a [MtgApiFailure.UnknownFailure] wrapping a [NullPointerException]
 * is produced. Use this when a non-null result is required by the domain layer.
 *
 * @param T The type of the original (nullable) success value.
 * @param R The type of the transformed success value.
 * @param transform A function applied to the non-null success value.
 * @return A new [MtgApiResult] containing the transformed non-null value, or a failure.
 */
inline fun <T, R> MtgApiResult<T?>.mapToNotNull(
    transform: (value: T) -> R
): MtgApiResult<R> = this.map {
    it?.let(transform) ?: throw MtgApiFailure.UnknownFailure(NullPointerException())
}

/**
 * Runs [block] with the success value if this result is a success, then returns this result unchanged.
 *
 * Useful for side-effects such as logging or updating UI state without breaking a chain.
 *
 * @param T The type of the success value.
 * @param block A side-effect block invoked with the success value.
 * @return This [MtgApiResult] unchanged.
 */
@Suppress("UNCHECKED_CAST")
inline fun <T> MtgApiResult<T>.onSuccess(
    block: (T) -> Unit
): MtgApiResult<T> {
    return if (isSuccess) {
        block(value as T)
        this
    } else {
        this
    }
}

/**
 * Runs [block] with the encapsulated [Throwable] if this result is a failure, then returns this result unchanged.
 *
 * Useful for side-effects such as error logging or reporting without breaking a chain.
 *
 * @param T The type of the success value.
 * @param block A side-effect block invoked with the failure [Throwable].
 * @return This [MtgApiResult] unchanged.
 */
inline fun <T> MtgApiResult<T>.onFailure(
    block: (Throwable) -> Unit
): MtgApiResult<T> {
    return if (isFailure) {
        block(value as Throwable)
        this
    } else {
        this
    }
}

