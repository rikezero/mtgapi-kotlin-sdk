package com.rikezero.mtgapi_kotlin_sdk.samples

import com.rikezero.mtgapi_kotlin_sdk.config.BuildConfig
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.impl.MtgApiRepositoryImpl
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetCardByIdUseCase
import com.rikezero.mtgapi_kotlin_sdk.networking.MtgApiNetworking
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.impl.MtgApiNetworkEngineImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.retrofit.buildMtgApiRetrofit
import com.rikezero.mtgapi_kotlin_sdk.networking.impl.MtgApiNetworkingImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter.impl.MtgApiNetworkAdapterImpl
import kotlinx.coroutines.runBlocking

/**
 * Standalone sample app that demonstrates how to use GetCardByIdUseCase.
 *
 * To run:
 * 1. Build: ./gradlew build
 * 2. Run: ./gradlew runCardByIdSample
 */
fun main() = runBlocking {
    println("=== MTG API Kotlin SDK - Card By ID Sample ===\n")

    try {
        val networking = setupNetworkingForCardById()
        val repository = MtgApiRepositoryImpl(networking)
        val useCase = GetCardByIdUseCase(repository)

        println("=== Example 1: Card ID 2 ===")
        val result1 = useCase("2")
        if (result1.isSuccess) {
            val card = result1.getOrNull()
            if (card != null) {
                println("Name:       ${card.name}")
                println("Mana Cost:  ${card.manaCost}")
                println("Type:       ${card.type}")
                println("Rarity:     ${card.rarity}")
                println("Set:        ${card.setName} (${card.set})")
                println("Power/Toughness: ${card.power}/${card.toughness}")
                println("Text:       ${card.text}")
            } else {
                println("Card not found (empty response)")
            }
        } else {
            println("Error: ${result1.exceptionOrNull()?.message}")
        }

        println("\n=== Example 2: Jace, the Mind Sculptor (ID 169721) ===")
        val result2 = useCase("169721")
        if (result2.isSuccess) {
            val card = result2.getOrNull()
            if (card != null) {
                println("Name:       ${card.name}")
                println("Mana Cost:  ${card.manaCost}")
                println("Type:       ${card.type}")
                println("Rarity:     ${card.rarity}")
                println("Set:        ${card.setName} (${card.set})")
                println("Text:       ${card.text}")
            } else {
                println("Card not found (empty response)")
            }
        } else {
            println("Error: ${result2.exceptionOrNull()?.message}")
        }

        println("\n=== Example 3: Invalid ID (error handling) ===")
        val result3 = useCase("invalid-id-abc")
        if (result3.isSuccess) {
            println("Card: ${result3.getOrNull()?.name}")
        } else {
            println("Expected error: ${result3.exceptionOrNull()?.message}")
        }

        println("\n=== Sample completed successfully! ===")

    } catch (e: Exception) {
        println("Error running sample: ${e.message}")
        e.printStackTrace()
    }
}

private fun setupNetworkingForCardById(): MtgApiNetworking {
    val retrofit = buildMtgApiRetrofit(
        host = BuildConfig.MAGIC_THE_GATHERING_BASE_URL,
        interceptors = listOf()
    )
    val networkEngine = MtgApiNetworkEngineImpl(retrofit)
    val networkAdapter = MtgApiNetworkAdapterImpl(networkEngine)
    return MtgApiNetworkingImpl(networkAdapter)
}
