// cypress/e2e/01-page-load-and-elements.cy.js

describe('Global News - Page Load and Main Elements', () => {
  beforeEach(() => {
    cy.visit('/');
    // I ensure the application is fully loaded and ready before each test.
    cy.waitForPageLoad(); // Waits for document.readyState === 'complete'
    cy.waitForAppReady(); // My custom command waits for the loading overlay to disappear
  });

  it('should load the main page and display static content', () => {
    // I verify the browser tab title contains the application name.
    cy.title().should('contain', 'Interactive News Globe');

    // I check that the main header, subtitle, author, and version badge are visible.
    cy.contains('Interactive News Globe').should('be.visible');
    cy.contains("Click a country or search to discover today's top story")
      .should('be.visible');
    cy.contains('By Omar Viquez').should('be.visible');
  });

  it('should display the main UI components (Globe and News section)', () => {
    // I confirm the globe visualization (SVG element) exists and is visible.
    // (Selector updated based on Globe.tsx)
    cy.get('svg.cursor-grab', { timeout: 15000 })
      .should('exist')
      .and('be.visible');

    // I verify the header for the news section is rendered.
    cy.contains('h1', 'News').should('be.visible'); // Assuming the 'News' header is an <h1> in NewsPanel.tsx
  });

  it('should handle responsiveness correctly across different viewports', () => {
    const viewports = [
      { width: 1920, height: 1080, device: 'desktop' },
      { width: 768, height: 1024, device: 'tablet' },
      { width: 375, height: 667, device: 'mobile' },
    ];

    viewports.forEach(viewport => {
      cy.viewport(viewport.width, viewport.height);
      cy.wait(500); // I allow a moment for the layout to adjust.

      // I re-verify that key elements remain visible on each viewport size.
      cy.contains('Interactive News Globe').should('be.visible');
      cy.contains('Click a country or search').should('be.visible');
      cy.contains('h1', 'News').should('be.visible');
    });
  });
});