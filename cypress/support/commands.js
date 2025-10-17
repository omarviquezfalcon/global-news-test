// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************

// Custom command to wait for page to be fully loaded
Cypress.Commands.add('waitForPageLoad', () => {
  cy.window().should('have.property', 'document')
  cy.document().should('have.property', 'readyState', 'complete')
})

// Custom command to check if globe is loaded
Cypress.Commands.add('waitForGlobeLoad', () => {
  cy.get('[data-testid="globe"], canvas, .globe-container', { timeout: 15000 })
    .should('be.visible')
})

// Custom command to check network requests
Cypress.Commands.add('waitForNetworkIdle', () => {
  cy.wait(2000) // Wait for network requests to settle
})