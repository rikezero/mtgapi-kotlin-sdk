---
created: 2026-05-04T00:00:00Z
last_edited: 2026-05-04T00:00:00Z
---

# Cavekit: Koin Integration Guide

## Scope
Defines content of docs/KOIN_INTEGRATION.md — Koin setup for Android and JVM consumers.

## Requirements

### R1: Prerequisites
**Acceptance Criteria:**
- [ ] Koin 3.4.2 dependency snippet present for both JVM and Android
- [ ] Coroutines dependency snippet present
- [ ] SDK dependency itself listed

### R2: Android Integration
**Acceptance Criteria:**
- [ ] Full Application class example shown with correct 3-step order
- [ ] `androidContext(this@MyApplication)` present inside startKoin block
- [ ] AndroidManifest.xml snippet with android:name present

### R3: JVM Integration
**Acceptance Criteria:**
- [ ] main() function example shown
- [ ] No Android-specific calls in JVM example
- [ ] Same 3-step order demonstrated

### R4: Module Loading Order
**Acceptance Criteria:**
- [ ] Mermaid flowchart present with startKoin → startMtgApiLibrary → loadKoinModules
- [ ] Explanation states startMtgApiLibrary calls loadKoinModules internally
- [ ] Explanation states dependent modules must load after SDK

### R5: Common Pitfalls
**Acceptance Criteria:**
- [ ] Three pitfalls documented
- [ ] Each has Symptom + Fix
- [ ] Covers: wrong init order, early injection, early dependent module loading

### R6: Testing
**Acceptance Criteria:**
- [ ] KoinTest example with @Before startKoin + startMtgApiLibrary
- [ ] stopKoin() in @After
- [ ] koin-test dependency snippet present

## Out of Scope
- Architecture internals (#26)
- Error handling (#31)
- README content (#24)
