---
created: 2026-05-04T00:00:00Z
last_edited: 2026-05-04T00:00:00Z
---

# Cavekit: Error Handling Guide

## Scope
Defines content of docs/ERROR_HANDLING.md — MtgApiFailure and MtgApiResult usage for SDK consumers.

## Requirements

### R1: MtgApiFailure Hierarchy
**Acceptance Criteria:**
- [ ] All concrete subtypes named exactly as in source
- [ ] Mermaid class diagram present
- [ ] Each subtype has description of when it occurs
- [ ] Inner Error class (if present) documented

### R2: MtgApiResult Usage
**Acceptance Criteria:**
- [ ] All extension functions listed by name
- [ ] At least one chaining example with onSuccess/onFailure
- [ ] mapToNotNull null-behavior explained (if function exists)

### R3: MtgApiResponse
**Acceptance Criteria:**
- [ ] Stated as internal/not exposed to consumers
- [ ] toResult() conversion logic described

### R4: Handling Patterns
**Acceptance Criteria:**
- [ ] Four patterns each have code snippet
- [ ] Pattern 3 uses when with actual subtype names from source
- [ ] Pattern 3 accesses typed fields (e.g. httpCode, error.message)

### R5: UI Best Practices
**Acceptance Criteria:**
- [ ] UI state sealed class defined with Loading/Success/Error
- [ ] ViewModel snippet mapping result to UI state
- [ ] Explicit statement: do not expose MtgApiFailure to UI

## Out of Scope
- Architecture internals (#26)
- Koin setup (#28)
- README content (#24)
