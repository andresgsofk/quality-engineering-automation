# 🚀 Quality Engineer Technical Challenge | Banco Pichincha

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Gradle](https://img.shields.io/badge/Gradle-Build-02303A?style=for-the-badge&logo=gradle)
![Serenity](https://img.shields.io/badge/Serenity-BDD-16A085?style=for-the-badge)
![Cucumber](https://img.shields.io/badge/Cucumber-BDD-23D96C?style=for-the-badge&logo=cucumber)
![Selenium](https://img.shields.io/badge/Selenium-WebDriver-43B02A?style=for-the-badge&logo=selenium)
![RestAssured](https://img.shields.io/badge/REST-Assured-6DB33F?style=for-the-badge)
![Screenplay](https://img.shields.io/badge/Pattern-Screenplay-blue?style=for-the-badge)

</div>

---

# 📌 Project Overview

Automated test framework developed as part of the **Quality Engineer Technical Challenge for Banco Pichincha**.

This solution implements a **hybrid automation architecture (WEB + API)** designed under **clean architecture principles**, **BDD**, and the **Screenplay Pattern**, ensuring scalability, maintainability, readability, and separation of concerns.

The framework validates:

✅ End-to-End WEB business flows  
✅ Financial calculation consistency  
✅ Business validation rules  
✅ Product comparison scenarios  
✅ REST API validations (positive & negative scenarios)  
✅ Reusable architecture for future expansion

---

# 👨‍💻 Author

## Andrés Gonzalez
**Systems Engineer | Quality Engineer Automation | SDET**

Technical challenge developed for:

🏦 **Banco Pichincha**

---

# 🏗 Architecture

```text
src/test/java/com/pichincha
│
├── runners
│   ├── ApiTestRunner.java
│   └── WebTestRunner.java
│
├── stepdefinitions
│   ├── api
│   └── web
│
├── api
│   ├── client
│   ├── constants
│   ├── models
│   ├── tasks
│   ├── questions
│   ├── validations
│   └── utils
│
├── simulator
│   ├── calculations
│   ├── constants
│   ├── models
│   ├── parsers
│   ├── questions
│   ├── tasks
│   ├── ui
│   └── validations
│
└── core
    ├── interactions
    ├── utils
    └── config
```

---

# 🎯 Implemented Scope

# 🌐 WEB Automation

## Flow 1 — Credit Simulation
Validation of successful financial simulation.

✔ Credit selection  
✔ Financial data entry  
✔ Simulation execution  
✔ Result validation

---

## Flow 2 — Simulation Result Validation
Validation of business output.

✔ Monthly payment  
✔ Interest rate  
✔ Amortization table

---

## Flow 3 — Financial Form Validations
Negative validations.

✔ Minimum amount validation  
✔ Required field validation  
✔ Product validation rules

---

## Flow 4 — Financial Calculation Validation
Mathematical validation against UI amortization table.

✔ Installment consistency  
✔ Interest validation  
✔ Insurance validation  
✔ Balance progression  
✔ Financial totals reconciliation

---

## Flow 5 — Product Comparison
Cross-product financial comparison.

✔ PRECISO vs HIPOTECARIO VIVIENDA  
✔ Rate comparison  
✔ Monthly fee comparison  
✔ Financial behavior validation

---

# 🔌 API Automation

Using:

https://fakestoreapi.com

Implemented scenarios:

✔ GET product by ID  
✔ GET products by category  
✔ GET products with pagination  
✔ POST create product  
✔ PUT update product  
✔ Negative invalid product lookup  
✔ Negative invalid category lookup  
✔ POST empty payload validation

---

# 🧠 Design Patterns & Best Practices

Implemented with:

✅ Screenplay Pattern  
✅ BDD with Gherkin  
✅ Clean Architecture  
✅ REST client encapsulation  
✅ Assertion separation  
✅ Reusable tasks  
✅ Explicit validations  
✅ Independent execution suites  
✅ Common reusable components  
✅ Defensive cookie banner handling  
✅ No hardcoded sleeps  
✅ Separation of concerns  
✅ WEB/API domain isolation

---

# 🍪 Cookie Banner Handling

The framework includes automatic handling of cookie consent banners.

Behavior:

```text
If cookie banner appears → Accept automatically
If cookie banner does not appear → Continue execution
```

This prevents flaky executions caused by browser session state differences.

---

# ⚙️ Technology Stack

| Technology | Version |
|---------|---------|
| Java | 21 |
| Gradle | Latest |
| Serenity BDD | Integrated |
| Cucumber | Integrated |
| Selenium WebDriver | Integrated |
| REST Assured | Integrated |
| JUnit | Integrated |

---

# ▶️ Execution

# 🌐 WEB Suite

Run from IDE:

```bash
WebTestRunner
```

Run from CLI:

```bash
gradlew clean test -Dcucumber.filter.tags="@web"
```

---

# 🔌 API Suite

Run from IDE:

```bash
ApiTestRunner
```

Run from CLI:

```bash
gradlew clean test -Dcucumber.filter.tags="@api"
```

---

# 🎯 Individual WEB Flows

Flow 1:

```bash
gradlew clean test -Dcucumber.filter.tags="@flujo1"
```

Flow 2:

```bash
gradlew clean test -Dcucumber.filter.tags="@flujo2"
```

Flow 3:

```bash
gradlew clean test -Dcucumber.filter.tags="@flujo3"
```

Flow 4:

```bash
gradlew clean test -Dcucumber.filter.tags="@flujo4"
```

Flow 5:

```bash
gradlew clean test -Dcucumber.filter.tags="@flujo5"
```

---

# 🔍 Individual API Scenarios

Positive scenarios:

```bash
gradlew clean test -Dcucumber.filter.tags="@api-positive"
```

Negative scenarios:

```bash
gradlew clean test -Dcucumber.filter.tags="@api-negative"
```

---

# 📊 Reports

Serenity reports generated at:

```bash
target/site/serenity/index.html
```

Includes:

✔ Screenshots  
✔ Request/Response logs  
✔ Assertions  
✔ Execution evidence  
✔ Detailed traceability

---

# 🔎 Key Technical Findings

## WEB Findings

Banco Pichincha simulator presents differentiated financial values:

- Approved capital shown in summary
- Real amortization base used in financial table

Framework validations were intentionally designed to validate actual financial behavior instead of superficial UI assumptions.

---

## API Findings

Fake Store API behavior:

- POST returns HTTP 201 Created
- Empty payload is still accepted by API
- Invalid product lookup returns HTTP 200 with empty body

These findings were intentionally validated and documented as part of the challenge analysis.

---

# 📈 Engineering Decisions

Why hybrid architecture?

Because modern Quality Engineering roles require automation capabilities across:

✔ UI automation  
✔ API automation  
✔ Business validation  
✔ Service integration

This framework demonstrates cross-domain automation capability with scalable design.

---

# 📦 Repository Setup

Clone repository:

```bash
git clone <repository-url>
```

Build:

```bash
gradlew clean build
```

Run complete suite:

```bash
gradlew clean test
```

---

# 🏁 Final Results

## WEB Coverage
✅ 5/5 Flows completed successfully

## API Coverage
✅ 8/8 Scenarios completed successfully

## Framework Status
✅ Fully Functional

---

# 💬 Final Note

This solution was designed not only to solve the technical challenge, but also to demonstrate production-level automation engineering practices focused on maintainability, scalability, technical quality, and clean software design.

---

<div align="center">

# ⭐ Banco Pichincha Quality Engineer Challenge Submission

Built with engineering discipline, automation best practices, and quality-first thinking.

</div>