package com.rikezero.mtgapi_kotlin_sdk.samples

import com.rikezero.mtgapi_kotlin_sdk.config.BuildConfig
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.impl.MtgApiRepositoryImpl
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetFormatsUseCase
import com.rikezero.mtgapi_kotlin_sdk.networking.MtgApiNetworking
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.impl.MtgApiNetworkEngineImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.retrofit.buildMtgApiRetrofit
import com.rikezero.mtgapi_kotlin_sdk.networking.impl.MtgApiNetworkingImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter.impl.MtgApiNetworkAdapterImpl
import kotlinx.coroutines.runBlocking

/**
 * Standalone sample app that demonstrates how to use GetFormatsUseCase.
 *
 * To run:
 * 1. Build: ./gradlew build
 * 2. Run: ./gradlew runFormatsSample
 */
fun main() = runBlocking {
    println("=== MTG API Kotlin SDK - Formats Sample ===\n")

    try {
        val networking = setupNetworkingForFormats()
        val repository = MtgApiRepositoryImpl(networking)
        val useCase = GetFormatsUseCase(repository)

        println("=== All Game Formats ===")
        val result = useCase(Unit)
        if (result.isSuccess) {
            val data = result.getOrNull()
            if (data != null && data.formats.isNotEmpty()) {
                println("Total formats: ${data.formats.size}")
                println()
                data.formats.forEach { format -> println("  - $format") }
            } else {
                println("No formats found (empty response)")
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

private fun setupNetworkingForFormats(): MtgApiNetworking {
    val retrofit = buildMtgApiRetrofit(
        host = BuildConfig.MAGIC_THE_GATHERING_BASE_URL,
        interceptors = listOf()
    )
    val networkEngine = MtgApiNetworkEngineImpl(retrofit)
    val networkAdapter = MtgApiNetworkAdapterImpl(networkEngine)
    return MtgApiNetworkingImpl(networkAdapter)
}
