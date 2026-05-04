---
created: 2026-05-04T00:00:00Z
last_edited: 2026-05-04T00:00:00Z
---

# Cavekit: Readme

## Scope

Defines the required content, structure, and correctness guarantees for the project's top-level README document. This kit covers what a consumer of the SDK must be able to learn from the README in order to install, initialize, perform a first successful API call, troubleshoot common setup mistakes, and locate generated reference documentation.

This kit applies only to the README document itself. It does not govern source code, generated API reference content, sample applications, or release artifacts beyond what the README must say about them.

## Requirements

### R1: Installation Instructions
**Description:** The README must describe three independent installation methods so a consumer can choose the one that fits their environment, and every version reference shown to the reader must reflect the current published version of the SDK.

**Acceptance Criteria:**
- [ ] An installation section exists and presents exactly three named installation methods.
- [ ] One installation method covers obtaining the SDK from a hosted package registry that requires authentication.
- [ ] One installation method covers obtaining the SDK from a public package registry that does not require authentication.
- [ ] One installation method covers manual installation from a downloadable archive artifact.
- [ ] Every concrete version identifier shown anywhere in the installation section equals the current published version `v1.0.0.81` (or its non-prefixed form `1.0.0.81` when the surrounding convention omits the `v`).
- [ ] No installation method references a version other than the current one.
- [ ] For the authenticated registry method, the reader is told that credentials are required and how those credentials are supplied to the build.
- [ ] For the manual method, the reader is told where to obtain the artifact and how it is added as a dependency.

**Dependencies:** None.

### R2: Quick Start / Basic Usage
**Description:** The README must contain a single, concise, end-to-end usage example that takes a reader from an initialized library to a handled API result, demonstrating dependency resolution, invoking a use case, and handling both the success and failure branches of the result type.

**Acceptance Criteria:**
- [ ] A section exists whose explicit purpose is showing first-call usage of the SDK (for example titled "Quick Start" or "Basic Usage").
- [ ] The section contains exactly one primary code example demonstrating end-to-end usage.
- [ ] The example shows obtaining a use case instance through dependency injection rather than by direct construction.
- [ ] The example shows invoking the card list use case to fetch cards.
- [ ] The example shows branching on the success state of the SDK's result type and accessing the success value.
- [ ] The example shows branching on the failure state of the SDK's result type and accessing the failure cause.
- [ ] The example is self-contained: every symbol referenced by the example is either defined within the example or imported from a clearly named package.
- [ ] The example assumes the library has already been initialized and explicitly cross-references the initialization section rather than duplicating its content.

**Dependencies:** R3 (initialization is a prerequisite of usage).

### R3: Library Initialization
**Description:** The README must explain how the library is initialized inside the consuming application's dependency injection container, covering both an Android application entry point and a non-Android JVM entry point, and must state the ordering constraints that make initialization correct.

**Acceptance Criteria:**
- [ ] A section exists dedicated to library initialization.
- [ ] The section presents an example for an Android application entry point.
- [ ] The section presents an example for a non-Android JVM entry point.
- [ ] Each example shows the consumer initializing their own dependency injection container before initializing the SDK.
- [ ] Each example shows the SDK initialization step as a single named call.
- [ ] The section explicitly states that SDK initialization must occur after the consumer's dependency injection container is started.
- [ ] The section explicitly states that SDK initialization must occur before any SDK feature is used.
- [ ] The non-Android example shows or describes loading consumer modules that depend on the SDK after the SDK initialization call.

**Dependencies:** R1 (installation is a prerequisite of initialization).

### R4: Troubleshooting
**Description:** The README must provide a troubleshooting section that diagnoses the most common setup failures a new consumer will encounter, so a reader hitting one of these failures can self-resolve without reading source code.

**Acceptance Criteria:**
- [ ] A section titled "Troubleshooting" (or equivalent) exists.
- [ ] The section documents the symptom and resolution for initializing the SDK before the consumer's dependency injection container has been started.
- [ ] The section documents the symptom and resolution for authentication failures when resolving the SDK from the authenticated package registry.
- [ ] The section documents the symptom and resolution for transitive dependency version conflicts between the SDK and the consuming project.
- [ ] Each documented issue states an observable symptom (for example, an error message or failure mode) and at least one corrective action.
- [ ] No documented issue requires the reader to inspect SDK source code to apply the resolution.

**Dependencies:** R3 (the ordering issue references the initialization contract defined there).

### R5: API Reference Link
**Description:** The README must direct readers to the published, generated API reference for the SDK, so consumers can look up types, parameters, and behaviors that are not covered by the narrative README content.

**Acceptance Criteria:**
- [ ] A section or clearly labeled link exists pointing the reader to the generated API reference documentation.
- [ ] The link target is reachable over HTTP and returns a successful response.
- [ ] The link is presented near the top-level navigation of the README (for example, in the overview, table of contents, or a dedicated documentation section) rather than buried inside an unrelated subsection.
- [ ] The link text identifies the destination as API reference / generated documentation and is not a bare URL.

**Dependencies:** None.

## Out of Scope

- Video tutorials, screencasts, or other non-text learning material.
- Marketing copy, comparisons against competing libraries, or adoption pitches beyond the existing brief overview.
- Architecture and internal design explanations (covered by a separate issue, #26).
- Deep coverage of error taxonomy, retry policies, or failure recovery patterns (covered by a separate issue, #31).
- Contribution guidelines, release process, or maintainer documentation.
- Localization or translation of the README into languages other than English.
- Per-endpoint usage walkthroughs beyond the single Quick Start example.

## Cross-References

- None. This kit is the only document-level kit currently planned. Future kits covering architecture documentation (#26) and error handling documentation (#31) should cross-reference R2 and R4 of this kit when they are drafted.
