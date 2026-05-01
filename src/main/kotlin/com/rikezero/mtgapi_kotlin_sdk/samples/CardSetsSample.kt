package com.rikezero.mtgapi_kotlin_sdk.samples

import com.rikezero.mtgapi_kotlin_sdk.config.BuildConfig
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.impl.MtgApiRepositoryImpl
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetSetsUseCase
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetSetsUseCase.SetParameters
import com.rikezero.mtgapi_kotlin_sdk.networking.MtgApiNetworking
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.impl.MtgApiNetworkEngineImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.retrofit.buildMtgApiRetrofit
import com.rikezero.mtgapi_kotlin_sdk.networking.impl.MtgApiNetworkingImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter.impl.MtgApiNetworkAdapterImpl
import kotlinx.coroutines.runBlocking

/**
 * Standalone sample app that demonstrates how to use GetSetsUseCase.
 *
 * To run:
 * 1. Build: ./gradlew build
 * 2. Run: ./gradlew runCardSetsSample
 */
fun main() = runBlocking {
    println("=== MTG API Kotlin SDK - Card Sets Sample ===\n")

    try {
        val networking = setupNetworkingForCardSets()
        val repository = MtgApiRepositoryImpl(networking)
        val useCase = GetSetsUseCase(repository)

        println("=== Example 1: All Sets (first 5) ===")
        val result1 = useCase(SetParameters())
        if (result1.isSuccess) {
            val data = result1.getOrNull()
            if (data != null) {
                println("Total sets fetched: ${data.sets.size}")
                println()
                data.sets.take(5).forEach { set ->
                    println("  [${set.code}] ${set.name}")
                    println("    Release: ${set.releaseDate} | Block: ${set.block ?: "N/A"}")
                }
                if (data.sets.size > 5) println("  ... and ${data.sets.size - 5} more")
            } else {
                println("No sets found (empty response)")
            }
        } else {
            println("Error: ${result1.exceptionOrNull()?.message}")
        }

        println("\n=== Example 2: Filter by name 'Modern' ===")
        val result2 = useCase(SetParameters(name = "Modern"))
        if (result2.isSuccess) {
            val data = result2.getOrNull()
            if (data != null) {
                println("Sets matching 'Modern': ${data.sets.size}")
                data.sets.forEach { set ->
                    println("  [${set.code}] ${set.name} | Release: ${set.releaseDate} | Block: ${set.block ?: "N/A"}")
                }
            } else {
                println("No sets found")
            }
        } else {
            println("Error: ${result2.exceptionOrNull()?.message}")
        }

        println("\n=== Example 3: Filter by block 'Ixalan' ===")
        val result3 = useCase(SetParameters(block = "Ixalan"))
        if (result3.isSuccess) {
            val data = result3.getOrNull()
            if (data != null) {
                println("Sets in 'Ixalan' block: ${data.sets.size}")
                data.sets.forEach { set ->
                    println("  [${set.code}] ${set.name} | Release: ${set.releaseDate}")
                }
            } else {
                println("No sets found")
            }
        } else {
            println("Error: ${result3.exceptionOrNull()?.message}")
        }

        println("\n=== Sample completed successfully! ===")

    } catch (e: Exception) {
        println("Error running sample: ${e.message}")
        e.printStackTrace()
    }
}

private fun setupNetworkingForCardSets(): MtgApiNetworking {
    val retrofit = buildMtgApiRetrofit(
        host = BuildConfig.MAGIC_THE_GATHERING_BASE_URL,
        interceptors = listOf()
    )
    val networkEngine = MtgApiNetworkEngineImpl(retrofit)
    val networkAdapter = MtgApiNetworkAdapterImpl(networkEngine)
    return MtgApiNetworkingImpl(networkAdapter)
}
