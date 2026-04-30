# Issue #76: Deserializer Bug - TODO Placeholder Blocking All API Calls

## Overview
The `MtgApiDeserializer` contains an unimplemented `JsonDeserializer` that throws `TODO("Not yet implemented")`, blocking all API calls across the SDK.

## Problem Statement
When attempting to use any API endpoint, the SDK fails with an "An operation is not implemented" error because the custom `JsonDeserializer` registered in `MtgApiDeserializer.getGson()` contains a `TODO` placeholder instead of actual implementation.

## Root Cause Analysis

### Current Implementation (Buggy)
```kotlin
object MtgApiDeserializer {
    fun <T : Any> getGson(responseKClass: KClass<T>): Gson = GsonBuilder()
        .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
        .enableComplexMapKeySerialization()
        .apply {
            registerTypeAdapter(
                responseKClass::class.java,
                object : JsonDeserializer<T> {
                    override fun deserialize(
                        json: JsonElement?,
                        typeOfT: Type?,
                        context: JsonDeserializationContext?
                    ): T {
                        TODO("Not yet implemented")  // ← Blocks all API calls
                    }
                }
            )
        }
        .create()
}
```

### Why This Fails
1. Every API response goes through `MtgApiDeserializer.getGson()`
2. The custom `JsonDeserializer` is registered for ALL response types
3. When Gson tries to deserialize, it calls the `deserialize()` method
4. The `TODO()` throws `AbstractMethodError` or similar
5. All API calls fail immediately

### Affected Components
- `MtgApiNetworkEngineImpl.parseResponse()` - calls `MtgApiDeserializer.getGson()`
- All API endpoints (cards, sets, types, etc.)
- Any code using `MtgApiRepositoryImpl`

## Acceptance Criteria

### Must Have
- [ ] Remove the unimplemented `JsonDeserializer` registration from `MtgApiDeserializer.getGson()`
- [ ] Remove unused imports: `JsonDeserializationContext`, `JsonDeserializer`, `JsonElement`, `Type`
- [ ] Verify Gson uses default deserialization behavior
- [ ] All existing response classes deserialize correctly without custom logic
- [ ] Unit tests pass for deserialization

### Verification Steps
1. Run `CardListSample` and verify it fetches real data from API
2. Run existing unit tests (if any)
3. Verify no "Not yet implemented" errors in logs

## Implementation Approach

### Option 1: Remove Custom Deserializer (Recommended)
Since the SDK uses Kotlin `@Serializable` annotations and Gson's default behavior should handle:
- Basic types (String, Int, Double, etc.)
- Collections (List, Map)
- Nested objects
- `@SerializedName` annotations

Simply remove the `JsonDeserializer` registration and let Gson handle deserialization natively.

### Changes Required
**File: `src/main/kotlin/com/rikezero/mtgapi_kotlin_sdk/networking/deserializer/MtgApiDeserializer.kt`**

Remove:
```kotlin
.apply {
    registerTypeAdapter(
        responseKClass::class.java,
        object : JsonDeserializer<T> {
            override fun deserialize(
                json: JsonElement?,
                typeOfT: Type?,
                context: JsonDeserializationContext?
            ): T {
                TODO("Not yet implemented")
            }
        }
    )
}
```

And remove unused imports:
- `com.google.gson.JsonDeserializationContext`
- `com.google.gson.JsonDeserializer`
- `com.google.gson.JsonElement`
- `java.lang.reflect.Type`

### Expected Result
```kotlin
object MtgApiDeserializer {
    fun <T : Any> getGson(responseKClass: KClass<T>): Gson = GsonBuilder()
        .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
        .enableComplexMapKeySerialization()
        .create()
}
```

## Testing Strategy

### Unit Tests
Create unit tests for:
1. `MtgApiDeserializer.getGson()` returns valid Gson instance
2. Gson correctly deserializes basic response types
3. Gson handles nested objects correctly
4. Gson respects `@SerializedName` annotations

### Test Files to Create
- `src/test/kotlin/com/rikezero/mtgapi_kotlin_sdk/networking/deserializer/MtgApiDeserializerTest.kt`

## Related Issues
- **Issue #22**: Card List Sample (blocked by this bug)
- **Issue #77**: Duplicate of #76 (closed)

## References
- [Magic: The Gathering API Documentation](https://docs.magicthegathering.io/)
- [Gson User Guide](https://github.com/google/gson/blob/main/gson/doc/UserGuide.md)
- `MtgApiNetworkEngineImpl.kt` - uses `MtgApiDeserializer.getGson()`
- `CardResponse.kt` - example response class with `@Serializable`

## Notes
- This is a critical bug blocking all SDK functionality
- The fix is safe because Gson's default behavior is sufficient for the SDK's needs
- No custom deserialization logic is required for the current response models
