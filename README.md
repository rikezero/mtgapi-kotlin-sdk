# Kotlin SDK for using the magicthegathering.io APIs

## Overview

This Kotlin SDK is built to interact with the [Magic: The Gathering API](https://magicthegathering.io), providing a clean,
Kotlin-centric interface to access the API's data. The SDK is designed to offer a more modern and structured approach with
features such as UseCases, tailored for better flexibility and ease of use compared to other SDKs available.

The SDK was developed to offer a more modern alternative to the officially listed Kotlin SDK for the Magic: The Gathering API,
which also don't appear to be maintained anymore.
It aims to provide developers with a better, more organized way to interact with the API while being easy to integrate into
your Kotlin-based applications.

The [Magic: The Gathering API documentation](https://docs.magicthegathering.io/#documentationgetting_started) can be found here
for a deeper understanding of how the API works.

Feel free to use or fork it as per your needs!

**WIP** - Ongoing updates to complete development and polish features.

## API Reference

Full generated API reference (KDoc): **[Releases → API Docs](https://github.com/rikezero/mtgapi-kotlin-sdk/releases)**

> Once KDoc generation via Dokka is published (see [#32](https://github.com/rikezero/mtgapi-kotlin-sdk/issues/32)), this link will point to the hosted API reference.

## Version
Current SDK version: v1.0.0.81

## Installation

There are three methods you can choose from to install the MTG API Kotlin SDK into your project: via
GitHub Packages or Maven Central (recommended) or by manually downloading the `.jar` file.

### Option 1: GitHub Packages

You can use GitHub Packages to install the SDK using Gradle. GitHub Packages requires authentication.

1. Add the GitHub Packages repository to your `build.gradle.kts` file:

    ```kotlin
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/rikezero/mtgapi-kotlin-sdk")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME_GITHUB")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("TOKEN_GITHUB")
            }
        }
    }
    ```

2. Add the dependency:

    ```kotlin
    dependencies {
        implementation("com.rikezero:mtgapi-kotlin-sdk:1.0.0.81")
    }
    ```

3. Authenticate by creating a [GitHub Personal Access Token](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry) with `read:packages` scope, then set `gpr.user` and `gpr.token` in your `~/.gradle/gradle.properties` (or as environment variables).

### Option 2: Maven Central (Recommended)

No authentication required.

1. Ensure `mavenCentral()` is in your repositories (it is by default in most projects):

   ```kotlin
   // settings.gradle.kts
   dependencyResolutionManagement {
       repositories {
           mavenCentral()
       }
   }
   ```

2. Add the dependency to your `build.gradle.kts`:

   ```kotlin
   dependencies {
       implementation("io.github.rikezero:mtgapi-kotlin-sdk:1.0.0.81")
   }
   ```

### Option 3: Manual Installation

1. Visit the [Releases page](https://github.com/rikezero/mtgapi-kotlin-sdk/releases) and download the `.jar` file for the release you want.
2. Place the `.jar` in your project's `libs/` directory.
3. Add it as a dependency in your `build.gradle.kts`:

    ```kotlin
    dependencies {
        implementation(files("libs/mtgapi-kotlin-sdk-1.0.0.81.jar"))
    }
    ```

## Initializing the Library in Koin

Once installed, initialize the SDK's Koin modules **after** starting Koin and **before** using any SDK feature.

### Android

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // 1. Start Koin with your own modules
        startKoin {
            androidContext(this@MyApplication)
            modules(myAppModules)
        }

        // 2. Load SDK modules into the running Koin instance
        startMtgApiLibrary()

        // 3. Load your modules that depend on SDK types (e.g. inject GetCardsUseCase)
        loadKoinModules(myModulesThatDependOnSdk)
    }
}
```

### Standalone JVM / Kotlin

```kotlin
import org.koin.core.context.startKoin

fun main() {
    // 1. Start Koin with your own modules
    startKoin {
        modules(myAppModules)
    }

    // 2. Load SDK modules into the running Koin instance
    startMtgApiLibrary()

    // 3. Load your modules that depend on SDK types
    loadKoinModules(myModulesThatDependOnSdk)
}
```

> **Order matters:** `startMtgApiLibrary()` must be called after `startKoin { }` but before injecting any SDK use case.

## Quick Start

After initialization, inject a use case and make your first API call:

```kotlin
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetCardsUseCase
import com.rikezero.mtgapi_kotlin_sdk.domain.usecase.GetCardsUseCase.GetCardsParams
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CardRepository : KoinComponent {

    private val getCards: GetCardsUseCase by inject()

    suspend fun fetchDragons() {
        val params = GetCardsParams(
            name = "Dragon",
            pageSize = 10
        )

        val result = getCards(params)

        result
            .onSuccess { cardList ->
                println("Found ${cardList.cards.size} cards")
                cardList.cards.forEach { println(it.name) }
            }
            .onFailure { error ->
                println("Error: ${error.message}")
            }
    }
}
```

> This example assumes the library is already initialized. See [Initializing the Library in Koin](#initializing-the-library-in-koin) above.

All 8 use cases follow the same pattern — see [Integration Samples](#integration-samples) for runnable examples of every endpoint.

## Integration Samples

The SDK includes standalone sample apps for every API endpoint. Each sample initializes dependencies manually (no host app required) and hits the real MagicTheGathering.io API, making them useful for quick endpoint validation.

### Available Samples

| Gradle Task | Sample | Endpoint |
|---|---|---|
| `runCardListSample` | Card List | `GET /v1/cards` |
| `runCardByIdSample` | Card By ID | `GET /v1/cards/{id}` |
| `runCardSetsSample` | Card Sets | `GET /v1/sets` |
| `runCardSetByCodeSample` | Card Set By Code | `GET /v1/sets/{code}` |
| `runTypesSample` | Types | `GET /v1/types` |
| `runSubtypesSample` | Subtypes | `GET /v1/subtypes` |
| `runSupertypesSample` | Supertypes | `GET /v1/supertypes` |
| `runFormatsSample` | Formats | `GET /v1/formats` |

### Running a Single Sample

```bash
./gradlew runCardListSample
./gradlew runCardByIdSample
./gradlew runCardSetsSample
./gradlew runCardSetByCodeSample
./gradlew runTypesSample
./gradlew runSubtypesSample
./gradlew runSupertypesSample
./gradlew runFormatsSample
```

### Running All Samples

```bash
./gradlew runAllSamples
```

### Sample Structure

Each sample follows the same pattern:

```kotlin
fun main() = runBlocking {
    // 1. Build dependencies manually — no host app or Koin needed
    val networking = setupNetworking()
    val repository = MtgApiRepositoryImpl(networking)
    val useCase = GetXxxUseCase(repository)

    // 2. Call the use case
    val result = useCase(params)

    // 3. Handle result
    result
        .onSuccess { println(it) }
        .onFailure { println("Error: ${it.message}") }
}
```

Sample files are located in:
```
src/main/kotlin/com/rikezero/mtgapi_kotlin_sdk/samples/
```

## Troubleshooting

### `startMtgApiLibrary()` called before `startKoin()`

**Symptom:** `KoinAppAlreadyStartedException` or `NoBeanDefFoundException` at startup before any API call is made.

**Fix:** Ensure `startKoin { }` is called first, then `startMtgApiLibrary()`:

```kotlin
startKoin { modules(...) }   // ← must come first
startMtgApiLibrary()          // ← then this
```

---

### GitHub Packages authentication failure

**Symptom:** Gradle sync fails with `401 Unauthorized` or `Could not resolve com.rikezero:mtgapi-kotlin-sdk`.

**Fix:**
1. Create a [GitHub Personal Access Token](https://github.com/settings/tokens) with the `read:packages` scope.
2. Add credentials to `~/.gradle/gradle.properties`:

   ```properties
   gpr.user=YOUR_GITHUB_USERNAME
   gpr.token=YOUR_PERSONAL_ACCESS_TOKEN
   ```

Alternatively, switch to **Maven Central** (Option 2) which requires no authentication.

---

### Transitive dependency version conflicts

**Symptom:** Build fails with `Duplicate class` errors or `Could not resolve` for Koin, OkHttp, or Retrofit transitive dependencies.

**Fix:** Force-resolve the conflicting version in your `build.gradle.kts`:

```kotlin
configurations.all {
    resolutionStrategy {
        force("io.insert-koin:koin-core:3.4.2")
    }
}
```

Check the [releases page](https://github.com/rikezero/mtgapi-kotlin-sdk/releases) for the dependency versions bundled with each SDK release.
