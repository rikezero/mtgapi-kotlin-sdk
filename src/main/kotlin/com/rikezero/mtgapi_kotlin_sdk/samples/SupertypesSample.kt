package com.rikezero.mtgapi_kotlin_sdk.samples

import com.rikezero.mtgapi_kotlin_sdk.config.BuildConfig
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.impl.MtgApiRepositoryImpl
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetSuperTypesUseCase
import com.rikezero.mtgapi_kotlin_sdk.networking.MtgApiNetworking
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.impl.MtgApiNetworkEngineImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.retrofit.buildMtgApiRetrofit
import com.rikezero.mtgapi_kotlin_sdk.networking.impl.MtgApiNetworkingImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter.impl.MtgApiNetworkAdapterImpl
import kotlinx.coroutines.runBlocking

/**
 * Standalone sample app that demonstrates how to use GetSuperTypesUseCase.
 *
 * To run:
 * 1. Build: ./gradlew build
 * 2. Run: ./gradlew runSupertypesSample
 */
fun main() = runBlocking {
    println("=== MTG API Kotlin SDK - Supertypes Sample ===\n")

    try {
        val networking = setupNetworkingForSupertypes()
        val repository = MtgApiRepositoryImpl(networking)
        val useCase = GetSuperTypesUseCase(repository)

        println("=== All Card Supertypes ===")
        val result = useCase(Unit)
        if (result.isSuccess) {
            val data = result.getOrNull()
            if (data != null && data.superTypes.isNotEmpty()) {
                println("Total supertypes: ${data.superTypes.size}")
                println()
                data.superTypes.forEach { supertype -> println("  - $supertype") }
            } else {
                println("No supertypes found (empty response)")
            }
        } else {
            println("Error: ${result.exceptionOrNull()?.message}")
        }

        println("\n=== Sample completed successfully! ===")

    } catch (e: Exception) {
        println("Error running sample: ${e.message}")
        e.printStackTrace()
    }
}

private fun setupNetworkingForSupertypes(): MtgApiNetworking {
    val retrofit = buildMtgApiRetrofit(
        host = BuildConfig.MAGIC_THE_GATHERING_BASE_URL,
        interceptors = listOf()
    )
    val networkEngine = MtgApiNetworkEngineImpl(retrofit)
    val networkAdapter = MtgApiNetworkAdapterImpl(networkEngine)
    return MtgApiNetworkingImpl(networkAdapter)
}
