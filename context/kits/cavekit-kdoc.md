---
created: 2026-05-04T00:00:00Z
last_edited: 2026-05-04T00:00:00Z
---

# Cavekit: KDoc Generation

## Scope
KDoc comments on all public API classes, methods, and properties.

## Requirements

### R1: Use Cases
**Acceptance Criteria:**
- [ ] All 8 use case classes have class-level KDoc
- [ ] Each use case's invoke/execute method has KDoc with @return
- [ ] GetCardsUseCase.GetCardsParams documents all query parameters with @param

### R2: Repository
**Acceptance Criteria:**
- [ ] MtgApiRepository interface has class-level KDoc
- [ ] All repository methods have @param and @return

### R3: Domain Models
**Acceptance Criteria:**
- [ ] All 10 domain model classes have class-level KDoc
- [ ] Non-obvious properties documented with @property

### R4: Result / Failure
**Acceptance Criteria:**
- [ ] MtgApiResult class/typealias has KDoc
- [ ] All extension functions (onSuccess, onFailure, map, mapToNotNull) have KDoc
- [ ] MtgApiFailure sealed class and all subtypes have KDoc stating when each occurs

### R5: Networking Interfaces
**Acceptance Criteria:**
- [ ] MtgApiNetworking, MtgApiNetworkAdapter, MtgApiNetworkEngine have class-level KDoc
- [ ] All interface methods have @param and @return

### R6: DI Entry Point
**Acceptance Criteria:**
- [ ] startMtgApiLibrary() has KDoc covering calling-order contract

## Out of Scope
- Dokka build configuration
- GitHub Pages publishing
- Internal implementation files (private classes)
