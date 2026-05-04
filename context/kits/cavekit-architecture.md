---
created: 2026-05-04T00:00:00Z
last_edited: 2026-05-04T00:00:00Z
---

# Cavekit: Architecture

## Scope
Defines the contents of a new architecture document (`docs/ARCHITECTURE.md`) for the `mtgapi-kotlin-sdk` project, addressing issue #26. Covers what the document must explain about layering, data flow, dependency injection composition, extension points, and the procedure for adding a new endpoint. Does not prescribe prose style, formatting choices, or diagram tooling.

## Requirements

### R1: Directory Structure
**Description:** The architecture document must present the package tree under `com.rikezero.mtgapi_kotlin_sdk/`, annotating each top-level package with its role. All five top-level packages must appear: `di/`, `domain/`, `networking/`, `util/`, `samples/`.
**Acceptance Criteria:**
- [ ] Document contains a section that renders the package tree rooted at `com.rikezero.mtgapi_kotlin_sdk/`.
- [ ] Each of the packages `di/`, `domain/`, `networking/`, `util/`, `samples/` is present in that tree.
- [ ] Each of those five packages has an inline annotation or adjacent description naming its role.
- [ ] No top-level package under `com.rikezero.mtgapi_kotlin_sdk/` that exists in the codebase is omitted from the tree.
**Dependencies:** None

### R2: Layer Responsibilities
**Description:** The document must define five architectural layers and assign explicit responsibilities to each, with a visual diagram showing their relationships.
**Acceptance Criteria:**
- [ ] A `networking/` layer is described as responsible for HTTP execution, the Retrofit service interface, response DTOs, and deserialization.
- [ ] A `networking/networkadapter/` + `networking/engine/` layer is described as an abstraction over the raw HTTP client that enables engine swaps.
- [ ] A `domain/repository/` layer is described as the contract isolating networking from domain, and explicitly names `MtgApiRepository` (interface) and `MtgApiRepositoryImpl`.
- [ ] A `domain/usecase/` layer is described as the consumer entry point, states each use case wraps one repository method, and names the base class `MtgApiUseCase<P, T>`.
- [ ] A `di/` layer is described as Koin module composition with three sub-modules wired via `startMtgApiLibrary()`.
- [ ] A Mermaid `flowchart` diagram is present showing the five layers and their directional dependencies (consumer → UseCase → Repository → Networking → Engine → HTTP).
**Dependencies:** R1

### R3: Data Flow
**Description:** The document must walk through the end-to-end sequence from a consumer's call to the returned result, rendered as a Mermaid sequence diagram.
**Acceptance Criteria:**
- [ ] A Mermaid `sequenceDiagram` block is present covering the full request/response cycle.
- [ ] The sequence shows a consumer invoking a UseCase with params.
- [ ] The sequence shows the Repository delegating to networking.
- [ ] The sequence shows the NetworkAdapter passing through to the NetworkEngine.
- [ ] The sequence shows a Retrofit HTTP call and ResponseBody deserialization.
- [ ] The sequence shows `MtgApiResponse` being converted to `MtgApiResult` via `ResponseExtensions`.
- [ ] The sequence shows a Mapper converting a Response DTO to a Domain Model.
- [ ] The sequence shows the final `MtgApiResult` being returned to the caller.
- [ ] The seven steps above appear in the order listed within the diagram.
**Dependencies:** R2

### R4: Koin DI Structure
**Description:** The document must describe how Koin modules are composed and exposed.
**Acceptance Criteria:**
- [ ] Three Koin modules are named: `mtgApiNetworkingModules`, `mtgApiRepositoryModules`, `mtgApiUseCaseModules`.
- [ ] The document states these are composed into a `mtgApiLibraryModules` list.
- [ ] The document identifies `startMtgApiLibrary()` as the single entry point and notes it calls `loadKoinModules()`.
- [ ] The document states the dependency chain: UseCase -> Repository -> Networking -> Adapter -> Engine -> Retrofit.
**Dependencies:** R2

### R5: Extension Points
**Description:** The document must enumerate four extension points available to consumers.
**Acceptance Criteria:**
- [ ] Extension point 1 is documented: OkHttp interceptor list passed via `buildMtgApiRetrofit(interceptors=...)`, with stated use cases of logging, auth, and caching.
- [ ] Extension point 2 is documented: per-request `JsonDeserializer<T>` override on `MtgApiNetworkAdapter.get(deserializer=...)`.
- [ ] Extension point 3 is documented: providing a custom `MtgApiRepository` implementation to swap the data source without changing use cases.
- [ ] Extension point 4 is documented: subclassing `MtgApiUseCase<P, T>` to add business logic on top of any repository method.
**Dependencies:** R2, R4

### R6: Adding a New Endpoint
**Description:** The document must include a numbered, ordered, ten-step checklist for adding a new endpoint, with each step naming the layer and the specific file or directory affected.
**Acceptance Criteria:**
- [ ] Step 1 specifies creating a Response DTO under `networking/response/`.
- [ ] Step 2 specifies creating a Domain Model under `domain/model/`.
- [ ] Step 3 specifies adding a mapper function in `domain/mappers/MtgApiMappers.kt`.
- [ ] Step 4 specifies adding a method to the repository interface in `domain/repository/MtgApiRepository.kt`.
- [ ] Step 5 specifies updating the repository implementation in `domain/repository/impl/MtgApiRepositoryImpl.kt`.
- [ ] Step 6 specifies adding a method to the networking interface in `networking/MtgApiNetworking.kt`.
- [ ] Step 7 specifies updating the networking implementation in `networking/impl/MtgApiNetworkingImpl.kt`.
- [ ] Step 8 specifies adding a Retrofit service method in `networking/service/MtgApiService.kt`.
- [ ] Step 9 specifies adding a UseCase class under `domain/usecase/`.
- [ ] Step 10 specifies registering bindings in `di/MtgApiModule.kt`.
- [ ] The ten steps appear in the order listed above.
**Dependencies:** R1, R2, R4

### R7: README Linkage
**Description:** The new architecture document must be discoverable from the project README.
**Acceptance Criteria:**
- [ ] `README.md` contains a link whose target resolves to `docs/ARCHITECTURE.md`.
- [ ] The architecture document exists at the path `docs/ARCHITECTURE.md`.
**Dependencies:** R1

## Out of Scope
- README structure or content beyond the single link to the architecture document (covered by issue #24 / cavekit-readme).
- In-depth treatment of error handling semantics beyond naming `MtgApiResult` in the data flow (covered by issue #31).
- KDoc generation, formatting, or publishing pipeline (covered by issue #32).
- A consumer-facing Koin integration guide; only the SDK's internal Koin composition is in scope (covered by issue #28).
- Internal implementation details that are not required to understand or extend the documented architecture.

## Cross-References
- See also: cavekit-readme.md (R24 overlap on the README link target)
