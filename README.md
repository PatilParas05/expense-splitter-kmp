# Expense Splitter KMP

A Kotlin Multiplatform (KMP) project where I built an expense splitting application with shared business logic and multiplatform targets.

## What I built

This repository contains a complete expense-splitting app architecture with:

- **Shared Kotlin module** for business/domain logic.
- **Android app** using Compose Multiplatform.
- **iOS integration** via shared Kotlin framework.
- **Desktop target** (JVM) configuration.
- **Web target** (WASM/JS) configuration.

The project is structured to maximize code reuse while keeping platform-specific integrations clean.

---

## Repository structure

```text
expense-splitter-kmp/
├── composeApp/               # App layer (Android + Desktop + WASM entry points)
├── shared/                   # Shared domain/data logic (KMP)
├── iosApp/                   # iOS Xcode project consuming Shared framework
├── kotlin-js-store/          # Web storage-related module/assets
├── gradle/                   # Gradle version catalog & wrapper settings
├── build.gradle.kts          # Root build configuration
├── settings.gradle.kts       # Module includes and dependency repositories
└── README.md
```

---

## Language composition

- **Kotlin**: 93.9%
- **Swift**: 4.2%
- **HTML**: 1.9%

This reflects a Kotlin-first KMP codebase with iOS integration and web-facing assets.

---

## Platforms and targets configured

From your Gradle setup, this project is configured for:

- **Android** (`androidTarget`)
- **iOS** (`iosArm64`, `iosSimulatorArm64`)
- **Desktop JVM** (`jvm("desktop")`)
- **WASM JS (browser)** (`wasmJs { browser(); binaries.executable() }`)

This is a strong multiplatform setup that supports modern Kotlin target coverage.

---

## Core technical work done in this repo

### 1) Multiplatform architecture foundation

You set up the project with clear separation between:

- **`shared` module** for reusable logic.
- **`composeApp` module** for UI/app-specific behavior.
- **`iosApp`** for native iOS host integration.

This is the right structure for long-term scalability in KMP apps.

### 2) Expense splitting domain implementation

The domain model and use cases in the shared module include:

- People management
- Expense recording
- Split calculations
- Settlement calculations

Your implementation includes multiple split strategies (equal/exact/percentage style separation), which is a good abstraction for extending business rules later.

### 3) Settlement algorithm flow

You implemented a settlement calculation flow that reduces balances into payable transfers (debtor/creditor style matching), enabling user-friendly “who pays whom” output.

### 4) Shared ViewModel approach

You used a shared ViewModel pattern in KMP (with MOKO MVVM) to keep state/business flow portable across platforms while still allowing platform-native presentation.

### 5) Dependency injection and modularization

You introduced **Koin** in shared and platform areas, enabling cleaner dependency management and improved testability as the app grows.

### 6) Local persistence groundwork

You configured **Room + KSP** for supported targets and explicitly avoided unsupported WASM paths, showing careful target-aware engineering.

### 7) Cross-platform storage strategy awareness

You kept target-specific constraints in mind:

- Room for Android/Desktop-class targets.
- Browser target separated from Room usage.

This is exactly how KMP persistence should be approached.

### 8) Build and packaging setup

You configured:

- Android app settings (SDK, packaging exclusions, release build type).
- Desktop distribution formats (**DMG/MSI/DEB**).
- WASM browser output.

This means the project is already aligned for multi-platform distribution workflows.

---

## Key dependencies and why they matter

- **Kotlin Coroutines**: async/state handling across platforms.
- **Koin**: dependency injection in common and platform code.
- **MOKO MVVM**: shared ViewModel/state pattern for KMP.
- **UUID library**: multiplatform-safe identifier generation.
- **Room + KSP**: persistence and code generation where supported.
- **Compose Multiplatform / Material 3**: modern declarative UI.

---

## Build commands

### Android build

```bash
./gradlew :composeApp:assembleDebug
```

### Shared tests

```bash
./gradlew :shared:allTests
```

### Desktop run/package

Use Compose Desktop tasks from `composeApp` (run/package tasks available through Gradle tooling and IDE integration).

### iOS

Open the Xcode project in `iosApp/` and run using simulator/device after syncing the shared framework.

---

## What is done well (project strengths)

- ✅ Strong **multiplatform-first** architecture.
- ✅ Good **module separation** between shared logic and app hosts.
- ✅ Thoughtful **target-specific persistence strategy**.
- ✅ Modern stack choices (Compose, Koin, Coroutines, Room, KSP).
- ✅ Scalable split/settlement business logic design.
- ✅ Desktop and WASM targets included early (future-ready setup).

---

## Suggested next improvements

To make the repo even stronger, consider adding:

1. **Screenshots/GIFs** for Android/iOS/Desktop UI.
2. **Architecture diagram** (data flow + module dependencies).
3. **Test coverage summary** for settlement/split edge cases.
4. **CI workflow** (build + test for PRs).
5. **Release notes/changelog** as features evolve.
6. **Contribution guide** (`CONTRIBUTING.md`) and issue templates.

---

## About this project

This repository demonstrates practical Kotlin Multiplatform development for a real-world finance utility app, with shared business logic and multi-target deployment foundations.

If you want, I can next generate:

- a polished **README with badges + screenshots placeholders**,
- a **developer setup guide** (`docs/setup.md`), and
- a **CI GitHub Actions workflow** for Android/shared checks.
