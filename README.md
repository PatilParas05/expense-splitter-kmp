# Expense Splitter KMP

A **Kotlin Multiplatform** (KMP) application for splitting expenses among a group of people. Runs on both **Android** (Compose) and **iOS** (SwiftUI).

---

## Features

- **Add People** — Add participants by name; each person is assigned a unique ID.
- **Add Expenses** — Record an expense amount and who paid. The cost is split equally among all current participants.
- **Calculate Settlements** — Computes the minimum set of transfers needed to settle all debts.
- **Cross-platform** — Shared business logic written once in Kotlin; native UIs on Android and iOS.

---

## Architecture

```
expense-splitter-kmp/
├── shared/                        # Shared Kotlin Multiplatform module
│   └── src/commonMain/
│       └── org/paraspatil/expensesplitter/
│           ├── domain/
│           │   ├── model/         # Person, Expense, Split
│           │   ├── settlement/    # Settlements model, calculateSettlements()
│           │   ├── split/         # SplitCalculator interface + Equal/Exact/Percentage implementations
│           │   ├── usecase/       # CalculateExpenseUseCase, ExpenseResult
│           │   └── CalculateBalances.kt
│           └── presentation/
│               ├── ExpenseViewModel.kt
│               └── ExpenseUiState.kt
├── composeApp/                    # Android app (Jetpack Compose + Material 3)
│   └── src/androidMain/
│       └── org/paraspatil/expensesplitter/
│           ├── App.kt
│           ├── MainActivity.kt
│           └── ui/expense/
│               ├── ExpenseRoute.kt
│               └── ExpenseScreen.kt
└── iosApp/                        # iOS app (SwiftUI)
    └── iosApp/
        ├── ContentView.swift      # SwiftUI UI + IOSExpenseViewModel
        └── iOSApp.swift
```

### Key Design Decisions

- **Shared ViewModel** (`ExpenseViewModel`) uses [MOKO MVVM](https://github.com/icerockdev/moko-mvvm) (`dev.icerock.moko:mvvm-core:0.16.1`) so it can be shared between Android and iOS.
- **UUID generation** uses [`com.benasher44:uuid`](https://github.com/benasher44/uuid) for multiplatform-safe UUIDs.
- **Split strategies** — `EqualSplitCalculator`, `ExactSplitCalculator`, `PercentageSplitCalculator` all implement the `SplitCalculator` interface.
- Settlement algorithm uses a greedy creditor/debtor matching approach to minimise the number of transfers.

---

## App Flow

1. Add persons by name — creates `Person(id, name)` objects stored in the ViewModel.
2. Add expenses — enter amount and select who paid — creates an `Expense` with equal `Split` objects across all persons.
3. Tap **Calculate Settlements** — runs `CalculateExpenseUseCase` — displays the list of settlements (e.g. "Alice owes Bob 250.00").

---

## Build & Run

### Prerequisites

- Android Studio Hedgehog (or newer) with KMP plugin
- JDK 17+
- Xcode 15+ (for iOS)

### Android

```shell
# macOS / Linux
./gradlew :composeApp:assembleDebug

# Windows
.\gradlew.bat :composeApp:assembleDebug
```

Or use the **Run** configuration in Android Studio.

### iOS

Open `iosApp/iosApp.xcodeproj` (or the `.xcworkspace` if using CocoaPods) in Xcode and run on a simulator or device.

### Run shared tests

```shell
./gradlew :shared:allTests
```

---

## Dependencies

| Library | Purpose |
|---------|---------|
| `dev.icerock.moko:mvvm-core:0.16.1` | Multiplatform ViewModel base class |
| `com.benasher44:uuid:0.8.0` | Multiplatform UUID generation |
| `org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1` | Coroutines / StateFlow |
| Jetpack Compose + Material 3 | Android UI |

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html).
