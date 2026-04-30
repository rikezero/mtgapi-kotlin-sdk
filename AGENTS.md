# MTG API Kotlin SDK

## Build Commands
- `./gradlew build` - Full build
- `./gradlew jar` - Compile and create JAR
- `./gradlew publish` - Publish to GitHub Packages/Maven Central (requires secrets)

## Project Structure
- Single-module Gradle project: `:mtgapi-kotlin-sdk`
- Source: `src/main/kotlin/com/rikezero/mtgapi_kotlin_sdk/`
- Architecture: Clean Architecture with UseCases, Repository, and Network layers

## Key Technologies
- Kotlin 2.0.20
- Koin 3.4.2 (DI)
- Retrofit2 2.11.0 + OkHttp3 4.11.0
- Kotlin Coroutines

## Version
- Base version in `version.properties` (e.g., `1.0.0`)
- Full version = `baseVersion.GITHUB_RUN_NUMBER` (set in CI)

## Publishing
- Uses `mavenDeployer` plugin for GitHub Packages and Maven Central
- Requires secrets: `GH_USERNAME`, `GH_TOKEN`, `OSSRH_USERNAME`, `OSSRH_PASSWORD`, `GPG_PRIVATE_KEY`, `GPG_PASSWORD`

## Dependency Injection
- Users must call `startMtgApiLibrary()` after `startKoin()` initialization
- Koin modules defined in `MtgApiModule.kt`