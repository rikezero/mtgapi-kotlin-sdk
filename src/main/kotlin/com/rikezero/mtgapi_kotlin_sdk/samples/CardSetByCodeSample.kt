package com.rikezero.mtgapi_kotlin_sdk.samples

import com.rikezero.mtgapi_kotlin_sdk.config.BuildConfig
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.impl.MtgApiRepositoryImpl
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetSetByIdUseCase
import com.rikezero.mtgapi_kotlin_sdk.networking.MtgApiNetworking
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.impl.MtgApiNetworkEngineImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.retrofit.buildMtgApiRetrofit
import com.rikezero.mtgapi_kotlin_sdk.networking.impl.MtgApiNetworkingImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter.impl.MtgApiNetworkAdapterImpl
import kotlinx.coroutines.runBlocking

/**
 * Standalone sample app that demonstrates how to use GetSetByIdUseCase.
 *
 * To run:
 * 1. Build: ./gradlew build
 * 2. Run: ./gradlew runCardSetByCodeSample
 */
fun main() = runBlocking {
    println("=== MTG API Kotlin SDK - Card Set By Code Sample ===\n")

    try {
        val networking = setupNetworkingForCardSetByCode()
        val repository = MtgApiRepositoryImpl(networking)
        val useCase = GetSetByIdUseCase(repository)

        println("=== Example 1: Set code M19 (Core Set 2019) ===")
        val result1 = useCase("M19")
        if (result1.isSuccess) {
            val set = result1.getOrNull()
            if (set != null) {
                println("Code:        ${set.code}")
                println("Name:        ${set.name}")
                println("Type:        ${set.type}")
                println("Release:     ${set.releaseDate}")
                println("Block:       ${set.block ?: "N/A"}")
                println("Online Only: ${set.onlineOnly}")
            } else {
                println("Set not found (empty response)")
            }
        } else {
            println("Error: ${result1.exceptionOrNull()?.message}")
        }

        println("\n=== Example 2: Set code THS (Theros) ===")
        val result2 = useCase("THS")
        if (result2.isSuccess) {
            val set = result2.getOrNull()
            if (set != null) {
                println("Code:        ${set.code}")
                println("Name:        ${set.name}")
                println("Type:        ${set.type}")
                println("Release:     ${set.releaseDate}")
                println("Block:       ${set.block ?: "N/A"}")
                println("Online Only: ${set.onlineOnly}")
            } else {
                println("Set not found (empty response)")
            }
        } else {
            println("Error: ${result2.exceptionOrNull()?.message}")
        }

        println("\n=== Example 3: Invalid code (error handling) ===")
        val result3 = useCase("INVALID")
        if (result3.isSuccess) {
            println("Set: ${result3.getOrNull()?.name}")
        } else {
            println("Expected error: ${result3.exceptionOrNull()?.message}")
        }

        println("\n=== Sample completed successfully! ===")

    } catch (e: Exception) {
        println("Error running sample: ${e.message}")
        e.printStackTrace()
    }
}

private fun setupNetworkingForCardSetByCode(): MtgApiNetworking {
    val retrofit = buildMtgApiRetrofit(
        host = BuildConfig.MAGIC_THE_GATHERING_BASE_URL,
        interceptors = listOf()
    )
    val networkEngine = MtgApiNetworkEngineImpl(retrofit)
    val networkAdapter = MtgApiNetworkAdapterImpl(networkEngine)
    return MtgApiNetworkingImpl(networkAdapter)
}
