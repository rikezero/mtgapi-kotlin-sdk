# Koin Integration Guide

This guide covers how to integrate the MTG API Kotlin SDK into your Android or JVM project using [Koin](https://insert-koin.io/) as the dependency injection framework.

---

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Android Integration](#android-integration)
3. [Standalone JVM Integration](#standalone-jvm-integration)
4. [Module Loading Order](#module-loading-order)
5. [What the SDK Registers](#what-the-sdk-registers)
6. [Common Pitfalls](#common-pitfalls)
7. [Testing with Koin Test](#testing-with-koin-test)

---

## Prerequisites

Add the following dependencies to your `build.gradle.kts`:

### JVM

```kotlin
dependencies {
    // MTG API Kotlin SDK
    implementation("io.github.rikezero:mtgapi-kotlin-sdk:1.0.0.81")

    // Koin for JVM
    implementation("io.insert-koin:koin-core:3.4.2")

    // Coroutines (required for use case execution)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}
```

### Android

```kotlin
dependencies {
    // MTG API Kotlin SDK
    implementation("io.github.rikezero:mtgapi-kotlin-sdk:1.0.0.81")

    // Koin for Android
    implementation("io.insert-koin:koin-android:3.4.2")

    // Coroutines (required for use case execution)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}
```

---

## Android Integration

### Application Class

Initialize everything in the correct three-step order inside your `Application.onCreate()`:

```kotlin
import android.app.Application
import com.rikezero.mtgapi_kotlin_sdk.di.startMtgApiLibrary
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Step 1: Start Koin with your application-level modules
        startKoin {
            androidContext(this@MyApplication)
            modules(myAppModules)
        }

        // Step 2: Load the SDK's Koin modules into the running Koin instance
        startMtgApiLibrary()

        // Step 3: Load your modules that depend on SDK-registered types
        loadKoinModules(myModulesThatDependOnSdk)
    }
}
```

### AndroidManifest.xml

Register your custom `Application` class in `AndroidManifest.xml`:

```xml
<application
    android:name=".MyApplication"
    android:label="@string/app_name"
    ... >

    <!-- activities, services, etc. -->

</application>
```

---

## Standalone JVM Integration

For JVM applications (desktop, server, CLI), the setup is identical except there is no `androidContext`:

```kotlin
import com.rikezero.mtgapi_kotlin_sdk.di.startMtgApiLibrary
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

fun main() {
    // Step 1: Start Koin with your application-level modules
    startKoin {
        modules(myAppModules)
    }

    // Step 2: Load the SDK's Koin modules into the running Koin instance
    startMtgApiLibrary()

    // Step 3: Load your modules that depend on SDK-registered types
    loadKoinModules(myModulesThatDependOnSdk)

    // Your application logic here
}
```

---

## Module Loading Order

The three-step initialization order is not optional — each step depends on the previous one completing successfully.

```mermaid
flowchart LR
    A["startKoin { }"] --> B["startMtgApiLibrary()"] --> C["loadKoinModules(yourDependentModules)"]
```

### Why Order Matters

**Step 1 — `startKoin { }`** must come first because it creates and starts the Koin container. Nothing else can run without a live Koin context.

**Step 2 — `startMtgApiLibrary()`** must come second. Internally, `startMtgApiLibrary()` calls `loadKoinModules()` to register all SDK bindings (networking, repository, use cases) into the already-running Koin context. If Koin has not been started yet, this call will throw because there is no container to load modules into.

**Step 3 — `loadKoinModules(yourDependentModules)`** must come last. Any module you write that injects SDK types (such as `GetCardsUseCase` or `MtgApiRepository`) uses Koin's `get()` to resolve those bindings at module load time. If the SDK modules have not been registered yet, `get()` will fail with a `NoBeanDefFoundException` because the SDK types do not exist in the container.

---

## What the SDK Registers

Calling `startMtgApiLibrary()` loads three internal Koin modules that together register the following singletons:

### Networking Layer (`mtgApiNetworkingModules`)

| Binding | Implementation | Notes |
|---|---|---|
| `Retrofit` (named `"retrofit_mtgapi"`) | `buildMtgApiRetrofit(...)` | Configured for `magicthegathering.io` base URL |
| `MtgApiNetworkEngine` | `MtgApiNetworkEngineImpl` | Depends on named Retrofit instance |
| `MtgApiNetworkAdapter` | `MtgApiNetworkAdapterImpl` | Depends on `MtgApiNetworkEngine` |
| `MtgApiNetworking` | `MtgApiNetworkingImpl` | Depends on `MtgApiNetworkAdapter` |

### Repository Layer (`mtgApiRepositoryModules`)

| Binding | Implementation | Notes |
|---|---|---|
| `MtgApiRepository` | `MtgApiRepositoryImpl` | Depends on `MtgApiNetworking` |

### Use Case Layer (`mtgApiUseCaseModules`)

| Binding | Implementation | Notes |
|---|---|---|
| `GetCardsUseCase` | `GetCardsUseCase` | Fetch a paginated list of cards |
| `GetCardByIdUseCase` | `GetCardByIdUseCase` | Fetch a single card by ID |
| `GetSetsUseCase` | `GetSetsUseCase` | Fetch a list of card sets |
| `GetSetByIdUseCase` | `GetSetByIdUseCase` | Fetch a single set by code |
| `GetFormatsUseCase` | `GetFormatsUseCase` | Fetch all supported formats |
| `GetTypesUseCase` | `GetTypesUseCase` | Fetch all card types |
| `GetSubTypesUseCase` | `GetSubTypesUseCase` | Fetch all card subtypes |
| `GetSuperTypesUseCase` | `GetSuperTypesUseCase` | Fetch all card supertypes |

All bindings are registered as **singletons** (`single { ... }`). Each use case depends on `MtgApiRepository`, which is resolved automatically by Koin.

---

## Common Pitfalls

### Pitfall 1: Calling `startMtgApiLibrary()` before `startKoin { }`

**Symptom:** `NoBeanDefFoundException` or `KoinAppAlreadyStartedException` thrown at application startup before any API call is made.

**Fix:** Always call `startKoin { }` first to create the Koin context, then call `startMtgApiLibrary()`:

```kotlin
// Correct
startKoin { modules(myAppModules) }
startMtgApiLibrary()

// Wrong — will throw
startMtgApiLibrary()  // no Koin context yet!
startKoin { modules(myAppModules) }
```

---

### Pitfall 2: Injecting an SDK use case before calling `startMtgApiLibrary()`

**Symptom:** `NoBeanDefFoundException` at the injection site when attempting to resolve `GetCardsUseCase`, `GetSetsUseCase`, or any other SDK type.

**Fix:** Ensure `startMtgApiLibrary()` is called during application startup (in `Application.onCreate()` or `main()`) before any component attempts to inject an SDK type:

```kotlin
// Correct — SDK modules are registered before injection
startKoin { modules(myAppModules) }
startMtgApiLibrary()

// Later, injection works:
val getCards: GetCardsUseCase by inject()

// Wrong — injecting before startMtgApiLibrary() throws
val getCards: GetCardsUseCase by inject()  // NoBeanDefFoundException!
startMtgApiLibrary()
```

---

### Pitfall 3: Loading dependent modules before calling `startMtgApiLibrary()`

**Symptom:** Runtime resolution failure — Koin cannot find SDK type bindings when loading your module, causing a crash during module loading rather than at the injection call site.

**Fix:** Always call `startMtgApiLibrary()` before `loadKoinModules()` for any module whose definitions call `get()` on SDK types:

```kotlin
// Correct
startKoin { modules(myAppModules) }
startMtgApiLibrary()
loadKoinModules(myModulesThatDependOnSdk)  // SDK types are available

// Wrong — get() cannot resolve SDK types yet
startKoin { modules(myAppModules) }
loadKoinModules(myModulesThatDependOnSdk)  // runtime resolution failure!
startMtgApiLibrary()
```

---

## Testing with Koin Test

### Dependency

Add the Koin test artifact to your test dependencies:

```kotlin
dependencies {
    testImplementation("io.insert-koin:koin-test:3.4.2")

    // For JUnit 4
    testImplementation("io.insert-koin:koin-test-junit4:3.4.2")

    // For JUnit 5
    // testImplementation("io.insert-koin:koin-test-junit5:3.4.2")
}
```

### Example Test Class

```kotlin
import com.rikezero.mtgapi_kotlin_sdk.di.startMtgApiLibrary
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetCardsUseCase
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetSetsUseCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class MtgApiModuleTest : KoinTest {

    private val getCards: GetCardsUseCase by inject()
    private val getSets: GetSetsUseCase by inject()

    @Before
    fun setUp() {
        // Step 1: Start a fresh Koin context for the test
        startKoin {
            // Add any test-specific modules here
            modules(emptyList())
        }

        // Step 2: Load the SDK modules
        startMtgApiLibrary()
    }

    @After
    fun tearDown() {
        // Always stop Koin after each test to avoid state leaking between tests
        stopKoin()
    }

    @Test
    fun `SDK use cases are resolvable from Koin`() {
        // If these injections succeed without throwing, the module is wired correctly
        assert(getCards != null)
        assert(getSets != null)
    }
}
```

> **Note:** Call `stopKoin()` in `@After` to tear down the Koin context between tests. Failing to do so will cause `KoinAppAlreadyStartedException` on the next test run.
