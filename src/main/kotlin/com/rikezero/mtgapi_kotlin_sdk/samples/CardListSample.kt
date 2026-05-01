package com.rikezero.mtgapi_kotlin_sdk.samples

import com.rikezero.mtgapi_kotlin_sdk.config.BuildConfig
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.impl.MtgApiRepositoryImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.MtgApiNetworking
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.MtgApiNetworkEngine
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.impl.MtgApiNetworkEngineImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.impl.MtgApiNetworkingImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter.MtgApiNetworkAdapter
import com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter.impl.MtgApiNetworkAdapterImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.retrofit.buildMtgApiRetrofit
import kotlinx.coroutines.runBlocking

/**
 * Standalone sample app that demonstrates how to use the MTG API Kotlin SDK.
 * 
 * This sample initializes all dependencies and fetches real data from the MagicTheGathering.io API.
 * It works like a consumer app would - without Koin DI.
 * 
 * To run:
 * 1. Build: ./gradlew build
 * 2. Run: ./gradlew runCardListSample
 */
fun main() = runBlocking {
    println("=== MTG API Kotlin SDK - Card List Sample ===\n")
    
    try {
        // Initialize all dependencies (like a consumer app would)
        val networking = setupNetworking()
        val repository = MtgApiRepositoryImpl(networking)
        
        // Example 1: Get all cards (first 10)
        println("=== Example 1: All Cards (first 10) ===")
        val result1 = repository.getCards(hashMapOf("pageSize" to "10"))
        handleResult(result1, "Cards")
        
        // Example 2: Filter by name
        println("\n=== Example 2: Cards with 'Black' in name ===")
        val result2 = repository.getCards(hashMapOf("name" to "Black", "pageSize" to "5"))
        handleResult(result2, "Filtered Cards")
        
        // Example 3: Pagination
        println("\n=== Example 3: Page 2 (10 cards per page) ===")
        val result3 = repository.getCards(hashMapOf("page" to "2", "pageSize" to "10"))
        handleResult(result3, "Page 2 Cards")
        
        // Example 4: Filter by type
        println("\n=== Example 4: Creature Cards ===")
        val result4 = repository.getCards(hashMapOf("type" to "Creature", "pageSize" to "5"))
        handleResult(result4, "Creature Cards")
        
        println("\n=== Sample completed successfully! ===")
        
    } catch (e: Exception) {
        println("Error running sample: ${e.message}")
        e.printStackTrace()
    }
}

/**
 * Sets up the networking layer with Retrofit and OkHttp
 */
private fun setupNetworking(): MtgApiNetworking {
    val retrofit = buildMtgApiRetrofit(
        host = BuildConfig.MAGIC_THE_GATHERING_BASE_URL,
        interceptors = listOf()
    )
    
    val networkEngine = MtgApiNetworkEngineImpl(retrofit)
    val networkAdapter = MtgApiNetworkAdapterImpl(networkEngine)
    return MtgApiNetworkingImpl(networkAdapter)
}

/**
 * Helper function to handle MtgApiResult and display results
 */
private fun <T> handleResult(result: com.rikezero.mtgapi_kotlin_sdk.domain.result.MtgApiResult<T>, title: String) {
    if (result.isSuccess) {
        val data = result.getOrNull()
        if (data != null) {
            println("Successfully fetched $title")
            println("Total items: ${getCardCount(data)}")
            println()
            
            // Print first few cards
            if (data is com.rikezero.mtgapi_kotlin_sdk.domain.model.lists.CardListModel) {
                data.cards.take(3).forEach { card ->
                    println("  - ${card.name} (${card.set})")
                }
                if (data.cards.size > 3) {
                    println("  ... and ${data.cards.size - 3} more")
                }
            }
        } else {
            println("Successfully fetched $title (empty response)")
        }
    } else {
        println("Error fetching $title: ${result.exceptionOrNull()?.message ?: "Unknown error"}")
    }
    println()
}

/**
 * Helper function to get card count from result data
 */
private fun getCardCount(data: Any): Int {
    return when (data) {
        is com.rikezero.mtgapi_kotlin_sdk.domain.model.lists.CardListModel -> data.cards.size
        else -> 0
    }
}
