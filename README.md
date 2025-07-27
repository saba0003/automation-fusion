# 🧑‍💻 Automation Fusion Project

**Automation Fusion** is a modular, scalable test automation framework that integrates
**Cucumber**, **Selenium**, **REST Assured**, and **TestNG**. It is built to streamline
the creation, execution, and maintenance of both API and UI automated tests, with clean
architecture and flexible configuration.

---

## 🧰 Tech Stack &nbsp;
![Java](https://img.shields.io/badge/Java-21-blue?logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-brightgreen?logo=apachemaven&logoColor=white)
![REST Assured](https://img.shields.io/badge/REST%20Assured-3C8DBC?logo=java&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-FF6F00?logo=testng&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-43B02A?logo=selenium&logoColor=white)
![WebDriverManager](https://img.shields.io/badge/WebDriverManager-A0A0A0?logo=java&logoColor=white)
![Cucumber](https://img.shields.io/badge/Cucumber-23D96C?logo=cucumber&logoColor=white)
![Gherkin](https://img.shields.io/badge/Gherkin-5C2D91?logoColor=white)
![Jackson](https://img.shields.io/badge/Jackson-0099CC?logo=jackson&logoColor=white)

---

## ✨ Features

- **Cucumber + Selenium Integration**  
  Write behavior-driven tests with Cucumber and automate browser interactions using Selenium WebDriver.

- **TestNG Support**  
  Takes advantage of TestNG for test lifecycle management, grouping, and parallel execution.

- **Configurable Test Properties**  
  Easily customize environment-specific configurations via external `config.properties` files.

- **Multi-Module, Scalable Design**  
  A Maven-based structure that promotes reusability, scalability, and separation of concerns.

- **REST Assured for API Testing**  
  Simplified and expressive DSL for RESTful API testing.

- **Detailed Reporting**  
  Supports readable, structured test reports for efficient test result analysis.

---

## 🏗️ Project Structure

The project is organized as a **Maven multi-module** build with the following layout:

```text
automation-fusion/
├── pom.xml           # Parent POM file
├── commons/          # Shared utilities and config loader
├── api-tests/        # REST Assured-based API test suite
└── ui-tests/         # UI tests with Selenium + Cucumber + Gherkin
```

---

## 📦 Maven Module Overview

### 🔹 [`pom.xml`](pom.xml) (Root Project)
- Declares all modules: [`commons`](./commons), [`api-tests`](./api-tests), and [`ui-tests`](./ui-tests)
- Sets `Java 21` as the source and target compatibility
- Declares shared dependencies (e.g., `slf4j-nop`, `jetbrains-annotations`)
- Configures essential Maven plugins:
    - `maven-compiler-plugin`
    - `maven-resources-plugin`
    - `maven-surefire-plugin`
        - Configures Maven Surefire plugin to recognize and run test classes matching the patterns:
            `*Test.java`, `*Tests.java`, and `*Runner.java` when executing `mvn test` from the terminal.
---

## 🛠️ Commons Module

- **Purpose:** Houses utility classes and configuration management code.
- **Key Components:**
    - [`ConfigReader`](./commons/src/main/java/utils/ConfigReader.java):
        - Loads the `config/config.properties` file once using a static block.
        - Provides a `get(String key)` method for fetching config values.
        - Throws:
            - [`ConfigException`](./commons/src/main/java/utils/ConfigException.java)
                custom exception if the file fails to load.
            - `NoSuchElementException` if a requested key is missing.

This module is used as a dependency by other modules to standardize configuration handling.

---

## 🧪 API Tests Module &nbsp;
![REST Assured](https://img.shields.io/badge/REST%20Assured-3C8DBC?logo=java&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-FF6F00?logo=testng&logoColor=white)
![Jackson](https://img.shields.io/badge/Jackson-0099CC?logo=jackson&logoColor=white)

- **Depends on:** [`commons`](./commons) module
- **Tools Used:**
    - **REST Assured** for HTTP request/response validation
    - **TestNG** for test organization and execution

- **Structure:**
    - Test class: [`PostsApiTests`](./api-tests/src/test/java/api/PostsApiTests.java)
    - Target API: [**https://jsonplaceholder.typicode.com**](https://jsonplaceholder.typicode.com)
    - Test Coverage:
        - CRUD operations on the `/posts` endpoint
        - Both **positive** and **negative** test cases
    - Includes a [`testng.xml`](./api-tests/src/test/resources/testng.xml) suite configuration:
        - Defines test groups
        - Organizes included test classes

---

## ⚗️ UI Tests Module &nbsp;
![Selenium](https://img.shields.io/badge/Selenium-43B02A?logo=selenium&logoColor=white)
![WebDriverManager](https://img.shields.io/badge/WebDriverManager-A0A0A0?logo=java&logoColor=white)
![Cucumber](https://img.shields.io/badge/Cucumber-23D96C?logo=cucumber&logoColor=white)
![Gherkin](https://img.shields.io/badge/Gherkin-5C2D91?logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-FF6F00?logo=testng&logoColor=white)

The **UI Tests Module** provides a robust framework for automating UI testing of the **SauceDemo** web application.  
It leverages industry-standard tools and design patterns to ensure maintainable, scalable, and readable test automation.

### 🏷️ UI Testing Scope

- **Selenium WebDriver** integration
- **Cucumber** step definitions
- **Page Object Model (POM)** design
- **UI regression testing**

### 🔖 Implementation Highlights

- **Selenium WebDriver Integration**  
  Automates browser interactions across multiple browsers
  (Chrome, Firefox, Edge) with `WebDriverManager` handling driver binaries.

- **Cucumber BDD Framework**  
  Uses Cucumber for behavior-driven development, enabling readable Gherkin `.feature` files and step definitions.

- **Page Object Model (POM)**  
  Encapsulates page elements and actions in dedicated page classes
  ([`LoginPage`](./ui-tests/src/test/java/pages/LoginPage.java),
   [`InventoryPage`](./ui-tests/src/test/java/pages/InventoryPage.java),
   [`CartPage`](./ui-tests/src/test/java/pages/CartPage.java))
  to promote reusability and maintainability.

- **TestNG Integration**  
  Runs Cucumber tests with TestNG, supporting test grouping, parallel execution, and detailed reporting.

- **Configurable Test Properties**  
  Browser type and application URLs are configurable via the shared
  [`config.properties`](./ui-tests/src/test/resources/config/config.properties) file.

- **Comprehensive Logging (Shared)**  
  Custom logging utilities are used **across all test modules** (API + UI) to provide clear, step-by-step
  execution logs. This enhances traceability and debugging across the entire automation suite.

---

## 📌 Requirements

- Java 21+
- Maven 3.8+
- IntelliJ IDEA with TestNG, Cucumber & Gherkin plugins

---

## 🔧 Configuration

Configuration is done through the `config.properties` files across modules.

This allows easy switching between test environments, base URLs, preferred browsers, etc.

---

## 🧾 View Test Execution Results

- [API Tests Reports](./reports/api/api-tests-reults.txt)
- [UI Login Tests Reports](./reports/ui/ui-tests-results-login.txt)
- [UI Cart Tests Reports](./reports/ui/ui-tests-results-cart.txt)
- [UI Logout Tests Reports](./reports/ui/ui-tests-results-logout.txt)

---

## 🚀 Getting Started

### To run all tests:

```bash
  # Run from root directory
  mvn clean test
```

### To update dependencies to newer versions
```bash
  # Run from root directory
  mvn versions:use-latest-releases
  
  # Run from the root directory to rebuild the project,
  # verify dependencies are updated and ensure tests aren't ruined
  mvn clean verify
```