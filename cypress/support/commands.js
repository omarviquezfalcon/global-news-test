// cypress/support/commands.js

/**
 * (RECOMMENDED) Waits for the application to be truly interactive.
 * Specifically, it waits for the initial loading overlay (z-50 spinner) to disappear.
 * I should call this after cy.visit() or after waiting for critical API calls.
 */
Cypress.Commands.add('waitForAppReady', () => {
  // From Icons.tsx, I know the GlobeSpinnerIcon has the 'animate-spin-slow' class.
  // I wait (up to 10s) for this element to no longer exist in the DOM,
  // indicating the initial data load and rendering is complete.
  cy.get('.animate-spin-slow', { timeout: 10000 }).should('not.exist');
});

/**
 * Waits for the base HTML page load to complete (document.readyState).
 * Useful as a preliminary check after cy.visit().
 */
Cypress.Commands.add('waitForPageLoad', () => {
  // I ensure the basic document structure is ready.
  cy.window().should('have.property', 'document');
  cy.document().should('have.property', 'readyState', 'complete');
});

/**
 * (DEPRECATED) Provides a fixed wait time.
 * Generally, I prefer using cy.waitForAppReady() or cy.wait('@interceptAlias')
 * for more reliable, condition-based waits instead of fixed timings.
 */
Cypress.Commands.add('waitForNetworkIdle', () => {
  // This provides a static 2-second pause.
  cy.wait(2000); // Maintained for legacy compatibility if used elsewhere.
});