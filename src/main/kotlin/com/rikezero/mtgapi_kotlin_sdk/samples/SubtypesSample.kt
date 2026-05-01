package com.rikezero.mtgapi_kotlin_sdk.samples

import com.rikezero.mtgapi_kotlin_sdk.config.BuildConfig
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.impl.MtgApiRepositoryImpl
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetSubTypesUseCase
import com.rikezero.mtgapi_kotlin_sdk.networking.MtgApiNetworking
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.impl.MtgApiNetworkEngineImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.retrofit.buildMtgApiRetrofit
import com.rikezero.mtgapi_kotlin_sdk.networking.impl.MtgApiNetworkingImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter.impl.MtgApiNetworkAdapterImpl
import kotlinx.coroutines.runBlocking

/**
 * Standalone sample app that demonstrates how to use GetSubTypesUseCase.
 *
 * To run:
 * 1. Build: ./gradlew build
 * 2. Run: ./gradlew runSubtypesSample
 */
fun main() = runBlocking {
    println("=== MTG API Kotlin SDK - Subtypes Sample ===\n")

    try {
        val networking = setupNetworkingForSubtypes()
        val repository = MtgApiRepositoryImpl(networking)
        val useCase = GetSubTypesUseCase(repository)

        println("=== All Card Subtypes ===")
        val result = useCase(Unit)
        if (result.isSuccess) {
            val data = result.getOrNull()
            if (data != null && data.subTypes.isNotEmpty()) {
                println("Total subtypes: ${data.subTypes.size}")
                println()
                println("First 20 subtypes:")
                data.subTypes.take(20).forEach { subtype -> println("  - $subtype") }
                if (data.subTypes.size > 20) println("  ... and ${data.subTypes.size - 20} more")
            } else {
                println("No subtypes found (empty response)")
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

private fun setupNetworkingForSubtypes(): MtgApiNetworking {
    val retrofit = buildMtgApiRetrofit(
        host = BuildConfig.MAGIC_THE_GATHERING_BASE_URL,
        interceptors = listOf()
    )
    val networkEngine = MtgApiNetworkEngineImpl(retrofit)
    val networkAdapter = MtgApiNetworkAdapterImpl(networkEngine)
    return MtgApiNetworkingImpl(networkAdapter)
}
