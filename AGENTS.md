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
- Kotlin JVM 17
- Retrofit2 + OkHttp3
- Gson serialization
- Koin (DI)
- Coroutines

## Testing Stack
- JUnit5 (NOT Kotest)
- MockK
- OkHttp MockWebServer
- Koin Test
- kotlinx-coroutines-test

## Coding Conventions
- Use suspend functions for all network calls
- Always inject dependencies via Koin, never instantiate directly
- Use sealed classes for error handling
- Test names follow: `should_expectedBehavior_when_condition`

## Version
- Base version in `version.properties` (e.g., `1.0.0`)
- Full version = `baseVersion.GITHUB_RUN_NUMBER` (set in CI)

## Publishing
- Uses `mavenDeployer` plugin for GitHub Packages and Maven Central
- Requires secrets: `GH_USERNAME`, `GH_TOKEN`, `OSSRH_USERNAME`, `OSSRH_PASSWORD`, `GPG_PRIVATE_KEY`, `GPG_PASSWORD`

## Dependency Injection
- Users must call `startMtgApiLibrary()` after `startKoin()` initialization
- Koin modules defined in `MtgApiModule.kt`

## GitHub Issues
When creating issues, use gh CLI with body-file:
```bash
gh issue create --title "title" --body-file /tmp/issue_body.md --label "label"
```
Always delete temp files after creating each issue.
Always create Epic first, then sub-issues, then update Epic with sub-issue numbers.

## Issue Labels
- epic - parent issue grouping sub-issues
- feature - new functionality
- bug - something broken
- unit-test - unit test implementation
- integration-test - integration test implementation
- refactor - code improvement without behavior change
- documentation - docs and KDoc
- chore - maintenance tasks
- good-first-issue - simple, well defined task
- ai-ready - issue is structured for agent implementation