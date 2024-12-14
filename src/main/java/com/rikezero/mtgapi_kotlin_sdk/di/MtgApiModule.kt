package com.rikezero.mtgapi_kotlin_sdk.di

import com.rikezero.mtgapi_kotlin_sdk.config.BuildConfig
import com.rikezero.mtgapi_kotlin_sdk.networking.MtgApiNetworking
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.MtgApiNetworkEngine
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.impl.MtgApiNetworkEngineImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.engine.retrofit.buildMtgApiRetrofit
import com.rikezero.mtgapi_kotlin_sdk.networking.impl.MtgApiNetworkingImpl
import com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter.MtgApiNetworkAdapter
import com.rikezero.mtgapi_kotlin_sdk.networking.networkadapter.impl.MtgApiNetworkAdapterImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val NAME_RETROFIT_MTG = "retrofit_mtgapi"

val mtgApiNetworkingModules = module {
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