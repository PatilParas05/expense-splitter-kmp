# 💸 ExpenseSplitter KMP

A modern, cross-platform expense sharing and debt settlement application built using **Kotlin Multiplatform** and **Compose Multiplatform**. Manage group expenses, track balances, and settle debts with a single click across Android, iOS, Desktop, and Web.

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.20-blue.svg?style=flat&logo=kotlin)
![Compose Multiplatform](https://img.shields.io/badge/Compose%20Multiplatform-1.6.0-purple.svg?style=flat&logo=jetpackcompose)
![Platform](https://img.shields.io/badge/Platform-Android%20%7C%20iOS%20%7C%20Desktop%20%7C%20Wasm-orange.svg)

---

## ✨ Features

- **👥 Multi-Person Groups** — Create sessions and add participants with unique IDs.
- **💰 Smart Expense Tracking** — Add expenses with descriptions, amounts, and specific payers.
- **⚖️ Settlement Algorithm** — Greedy matching algorithm minimizes transfers to clear debts.
- **📁 Session Management** — Save, rename, and switch between different expense groups.
- **🎨 Material 3 UI** — Adaptive interface with Dark/Light mode support.
- **🚀 High Performance** — Local-first architecture using Room Database for instant persistence.

---

# 🛠 Tech Stack

| Layer | Technology |
|--------|------------|
| UI Framework | Compose Multiplatform |
| Language | Kotlin |
| Architecture | MVVM |
| Database | Room Multiplatform |
| Dependency Injection | Koin |
| ViewModel |  MVVM |
| Asynchronous Programming | Kotlin Coroutines & Flow |
| Serialization | Kotlinx Serialization |

---

## 🚀 How to Run

### Prerequisites

Make sure you have the following installed:

- JDK 17+
- Android Studio Hedgehog or newer

---

## 📱 Android

### Using Android Studio

1. Open the project.
2. Select **composeApp** as the run configuration.
3. Click **Run**.

### Using Terminal

**Android Studio**

```bash
.\gradlew :composeApp:installDebug
```

---

## 💻 Desktop (Windows, macOS, Linux)

Run the native desktop application.

**Android Studio**

```bash
.\gradlew :composeApp:run
```

---

## 🌐 Web (Wasm JS)

Compile Kotlin to WebAssembly and launch the browser.

**Android Studio**

```bash
.\gradlew :composeApp:wasmJsBrowserRun
```


Open your browser and visit:

```
http://localhost:8080
```

---


## 🧮 Expense Splitting Logic

The application doesn't simply divide numbers—it intelligently minimizes the number of transactions using a **Greedy Settlement Algorithm**.

---

## ✅ Supported Split Types

### 1. Equal Split

The total expense is divided equally among all participants.

Example:

Expense = ₹1200

Participants = 3

Each person owes:

```
₹400
```

---

### 2. Exact Split

Each participant pays a manually specified amount.

Example:

| Person | Amount |
|---------|--------|
| Alice | ₹500 |
| Bob | ₹300 |
| Charlie | ₹400 |

Total = ₹1200

---

### 3. Percentage Split

Expenses are divided according to percentages.

Example:

| Person | Percentage |
|---------|------------|
| Alice | 50% |
| Bob | 30% |
| Charlie | 20% |

Expense = ₹1000

Result:

- Alice → ₹500
- Bob → ₹300
- Charlie → ₹200

---

## ⚡ Greedy Settlement Algorithm

The goal is to **minimize the total number of money transfers**.

Instead of asking everyone to pay everyone else, the algorithm computes the smallest possible set of transactions.

---

## Step 1 — Calculate Net Balance

For each participant:

```
Net Balance = Total Paid − Total Share
```

### Positive Balance (+)

The participant paid more than their share.

➡️ They should receive money.

### Negative Balance (-)

The participant paid less than their share.

➡️ They owe money.

---

## Step 2 — Match Debtors and Creditors

The algorithm repeatedly:

- Finds the person who owes the most.
- Finds the person who should receive the most.
- Creates a transaction between them.
- Updates both balances.

---

## Step 3 — Repeat

Continue matching until every participant's balance becomes zero.

---

# 📌 Example

Suppose:

Alice pays **₹1200** for Bob and Charlie.

Each person should pay:

```
₹400
```

Balances become:

| Person | Balance |
|---------|---------|
| Alice | +₹800 |
| Bob | -₹400 |
| Charlie | -₹400 |

Optimized settlement:

```
Bob  → Alice : ₹400
Charlie → Alice : ₹400
```

Only **2 transactions** are required.

---


## 🤝 Contributing

Contributions are welcome! If you'd like to improve the settlement algorithm, add new split types (Percentage/Exact), or improve the UI:
1. Fork the Project.
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`).
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`).
4. Push to the Branch (`git push origin feature/AmazingFeature`).
5. Open a Pull Request.

---

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

---
**Developed with ❤️ using Kotlin Multiplatform**


