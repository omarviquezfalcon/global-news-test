# 🧪 Multi-Framework Testing Suite

[![Tests](https://github.com/omarviquezfalcon/global-news-test/actions/workflows/tests.yml/badge.svg)](https://github.com/omarviquezfalcon/global-news-test/actions/workflows/tests.yml)

## 🎯 Project Overview

Professional-grade automated testing suite for **Global News Interactive Globe** implemented across three frameworks:

- 🐍 **Python + Selenium + Pytest** (20 tests)
- ☕ **Java + Maven + TestNG** (7 tests)  
- 🌲 **Cypress E2E** (25 tests)

**Total: 51 automated tests with 98% success rate**

## 🚀 Key Features

- ✅ **Multi-Framework Coverage**: Complete testing with Python, Java, and Cypress
- 📊 **Advanced Reporting**: HTML reports and Allure integration
- 🎥 **Video Evidence**: Cypress test recordings  
- 🎛️ **Consolidated Dashboard**: Unified view of all test results
- 🔄 **CI/CD Integration**: GitHub Actions workflow
- 📱 **Cross-Platform**: Windows and Linux automation scripts

## 🧪 Test Coverage

This comprehensive test suite covers critical user flows, edge cases, and API resilience scenarios:

  * **Page Load & Responsiveness:** Verifies core UI elements render correctly across various viewports
  * **Globe Interaction:** Tests country selection, click events, and visual feedback
  * **News Panel:** Validates states, content display, and user interactions  
  * **Search Functionality:** Autocompletion, keyboard navigation, and selection behavior
  * **API Resilience:** Error handling, timeout scenarios, and fallback mechanisms
  * **Settings Panel:** Configuration options and user preference management
  * **Donation Integration:** PayPal form preparation and validation

## 📊 Results Summary

| Framework | Tests | Passed | Failed | Pending | Success Rate |
|-----------|-------|---------|---------|---------|-------------|
| 🐍 Python | 20 | 20 | 0 | 0 | 100% |
| ☕ Java | 7 | 7 | 0 | 0 | 100% |
| 🌲 Cypress | 25 | 24 | 0 | 1 | 96% |
| **TOTAL** | **52** | **51** | **0** | **1** | **98%** |
  * **API Resilience:** Uses `cy.intercept()` to simulate API failures (500 errors) and empty responses, ensuring the UI handles these gracefully.
  * **User Interactions:** Validates panel closing mechanisms (button click, Escape key), external link behavior (`target="_blank"`), settings panel adjustments, and donation form preparation.
  * **Integration Testing:** Employs `cy.stub()` to verify interactions with third-party APIs (like PayPal form submission) without leaving the application context.

### Key Features

  * **Custom Commands:** Utilizes commands like `cy.waitForAppReady()` to handle application-specific loading states (e.g., waiting for loading overlays to disappear), enhancing test reliability.
  * **CI/CD Integration:** Fully integrated with GitHub Actions. Tests automatically run against Vercel Preview Deployments for every Pull Request, preventing regressions from reaching production. Branch protection rules enforce passing tests before merging to `main`.

### Running Tests Locally

1.  **Open Cypress (Interactive Mode):**
    ```sh
    npm run cypress:open
    ```
2.  **Run Cypress (Headless Mode):**
    ```sh
    npm run cypress:run
    ```

-----