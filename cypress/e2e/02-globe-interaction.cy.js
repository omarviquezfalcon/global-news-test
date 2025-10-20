// cypress/e2e/02-globe-interaction.cy.js

describe('Global News - Globe Interaction', () => {
  beforeEach(() => {
    cy.visit('/');
    // I ensure the application is fully loaded before each test.
    cy.waitForPageLoad();
    cy.waitForAppReady(); // My custom command waits for the loading overlay.

    // I confirm the globe SVG is visible and ready for interaction.
    cy.get('svg.cursor-grab', { timeout: 15000 }).should('be.visible');
  });

  it('should display the news panel when clicking on a country path', () => {
    // I am testing the primary user interaction with the globe.

    // I target the first country path element within the SVG.
    // Based on Globe.tsx, these paths handle the onCountryClick event.
    cy.get('path.country-path', { timeout: 10000 })
      .first()
      .click({ force: true }); // I use force:true to handle potential animations.

    // I verify that the news panel <aside> element becomes visible.
    // This confirms the state change from the click event was successful.
    cy.get('aside.max-w-md', { timeout: 10000 })
      .should('be.visible')
      .and('not.have.css', 'visibility', 'hidden');

    // As a secondary check, I confirm the inner scrollable container is also visible.
    cy.get('.flex-grow.overflow-y-auto').should('be.visible');
  });

  it('should allow basic keyboard navigation without crashing', () => {
    // This test ensures that keyboard events on the body
    // (like arrow keys for rotation) do not cause unexpected side effects.

    // I simulate arrow key presses.
    cy.get('body').focus().type('{leftarrow}{rightarrow}{uparrow}{downarrow}');
    cy.wait(500);

    // I verify the main application elements are still present.
    cy.contains('Interactive News Globe').should('be.visible');
    
    // I also verify that this action did not accidentally open the news panel.
    cy.get('aside.max-w-md')
      .should('not.be.visible')
      .and('have.css', 'visibility', 'hidden');
  });
});