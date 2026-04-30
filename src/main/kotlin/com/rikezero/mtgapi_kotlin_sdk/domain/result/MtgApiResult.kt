package com.rikezero.mtgapi_kotlin_sdk.domain.result

import com.rikezero.mtgapi_kotlin_sdk.domain.failure.MtgApiFailure

open class MtgApiResult<out R> @PublishedApi internal constructor(
    @PublishedApi
    internal val value: Any? = null
){
    val isSuccess: Boolean = value !is Throwable
    val isFailure: Boolean = value is Throwable

    @Suppress("UNCHECKED_CAST")
    fun getOrNull(): R? = when {
        isFailure -> null
        else -> value as R
    }

    fun exceptionOrNull(): Throwable? = when {
        isFailure -> value as Throwable?
        else -> null
    }

    companion object{
        fun <T> success(value: T): MtgApiResult<T> = MtgApiResult(value)
        fun <T> failure(throwable: Throwable): MtgApiResult<T> = MtgApiResult(throwable)
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <T, R> MtgApiResult<T>.map(
    transform: (value: T) -> R
): MtgApiResult<R> = when {
    isSuccess -> MtgApiResult.success(transform(value as T))
    else -> MtgApiResult.failure(value as Throwable)
}

inline fun <T, R> MtgApiResult<T?>.mapToNotNull(
    transform: (value: T) -> R
): MtgApiResult<R> = this.map {
    it?.let(transform) ?: throw MtgApiFailure.UnknownFailure(NullPointerException())
}

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

