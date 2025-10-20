## 🧪 Cypress E2E Test Suite

This project includes a comprehensive End-to-End (E2E) test suite built with **Cypress** to ensure application quality and stability.

### Strategy & Coverage

The tests are organized by application feature, covering critical user flows, edge cases, and API resilience scenarios. Key areas include:

  * **Initial Load & Responsiveness:** Verifies core UI elements render correctly across various viewports.
  * **Core Functionality:** Tests globe interaction, search functionality (including keyboard navigation), and news panel display.
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