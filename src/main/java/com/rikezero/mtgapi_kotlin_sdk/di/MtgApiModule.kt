package com.rikezero.mtgapi_kotlin_sdk.di

import com.rikezero.mtgapi_kotlin_sdk.config.BuildConfig
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.MtgApiRepository
import com.rikezero.mtgapi_kotlin_sdk.domain.repository.impl.MtgApiRepositoryImpl
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetCardByIdUseCase
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetCardsUseCase
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetFormatsUseCase
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetSetByIdUseCase
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetSetsUseCase
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetSubTypesUseCase
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetSuperTypesUseCase
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetTypesUseCase
import com.rikezero.mtgapi_kotlin_sdk.networking.MtgApiNetworking
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.MtgApiNetworkEngine
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.impl.MtgApiNetworkEngineImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.retrofit.buildMtgApiRetrofit
import com.rikezero.mtgapi_kotlin_sdk.networking.impl.MtgApiNetworkingImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter.MtgApiNetworkAdapter
import com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter.impl.MtgApiNetworkAdapterImpl
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val NAME_RETROFIT_MTG = "retrofit_mtgapi"

private val mtgApiNetworkingModules = module {
    single(named(NAME_RETROFIT_MTG)) {
        buildMtgApiRetrofit(
            host = BuildConfig.MAGIC_THE_GATHERING_BASE_URL,
            interceptors = listOf()
        )
    }
    single<MtgApiNetworkEngine> { MtgApiNetworkEngineImpl(retrofit = get()) }
    single<MtgApiNetworkAdapter> { MtgApiNetworkAdapterImpl(networkEngine = get()) }
    single<MtgApiNetworking> { MtgApiNetworkingImpl(networkAdapter = get()) }
}

private val mtgApiRepositoryModules = module {
    single<MtgApiRepository> { MtgApiRepositoryImpl(mtgApiNetworking = get()) }
}

private val mtgApiUseCaseModules = module {
    single<GetCardsUseCase> { GetCardsUseCase(mtgApiRepository = get()) }
    single<GetCardByIdUseCase> { GetCardByIdUseCase(mtgApiRepository = get()) }
    single<GetSetsUseCase> { GetSetsUseCase(mtgApiRepository = get()) }
    single<GetSetByIdUseCase> { GetSetByIdUseCase(mtgApiRepository = get()) }
    single<GetFormatsUseCase> { GetFormatsUseCase(mtgApiRepository = get()) }
    single<GetTypesUseCase> { GetTypesUseCase(mtgApiRepository = get()) }
    single<GetSubTypesUseCase> { GetSubTypesUseCase(mtgApiRepository = get()) }
    single<GetSuperTypesUseCase> { GetSuperTypesUseCase(mtgApiRepository = get()) }
}

private val mtgApiLibraryModules = listOf(
    mtgApiUseCaseModules,
    mtgApiRepositoryModules,
    mtgApiNetworkingModules
)

/**
 * Initializes the MTG API library by loading the necessary Koin modules.
 *
 * This function is responsible for setting up the dependencies required for the MTG API library.
 * It should be called **before** using any features or services provided by the library to ensure
 * that the necessary modules are loaded into the Koin dependency injection container.
 *
 * This function should be called after Koin has been initialized in the application (e.g., after
 * calling `startKoin()`), and it will load the modules specific to the MTG API library.
 *
 * Calling this function multiple times has no effect; the modules will only be loaded once.
 *
 * Example usage:
 * ```
 * // In your Application class, Koin is initialized first:
 * class MyApplication : Application() {
 *     override fun onCreate() {
 *         super.onCreate() *
 *         // Initialize Koin when the app starts
 *         startKoin {
 *             androidContext(this@MyApplication)
 *             modules(initialModules)  // This could be your global/default modules
 *         } *
 *         // Now, load the MTG API library modules dynamically
 *         startMtgApiLibrary()
 *     }
 * }
 * ```
 */
fun startMtgApiLibrary() {
    loadKoinModules(mtgApiLibraryModules)
}