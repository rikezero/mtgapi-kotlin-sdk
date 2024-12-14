package com.rikezero.mtgapi_kotlin_sdk.domain.usecase.base

import com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult

/**
 * A base UseCase that encapsulates a unit of business logic.
 *
 * @param P The type of input parameter.
 * @param T The type of the wrapped result produced by this use case.
 */
abstract class MtgApiUseCase<in P, T> {

    /**
     * Execute the use case logic with the given parameters.
     *
     * @param params Parameters required to execute the use case.
     * @return The result of executing this use case, wrapped in [MtgApiResult].
     *
     * This function is `operator` so it can be invoked using the `useCase(params)` syntax.
     *
     * It's marked `suspend` to support asynchronous operations, which is common in use cases.
     */
    suspend operator fun invoke(params: P): MtgApiResult<T> {
        return execute(params)
    }

    /**
     * Implement this method in subclasses to define the actual business logic.
     *
     * @param params Parameters for executing the use case.
     * @return The business logic result wrapped in [MtgApiResult].
     */
    protected abstract suspend fun execute(params: P): MtgApiResult<T>
}
