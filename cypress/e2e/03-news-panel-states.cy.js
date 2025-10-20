// cypress/e2e/03-news-panel-states.cy.js

describe('Global News - News Panel States', () => {

  // I am testing the default state of the application on initial load.
  context('Default State (No Data)', () => {
    beforeEach(() => {
      cy.visit('/');
      cy.waitForPageLoad();
      
      // I wait for the app to be fully interactive.
      cy.waitForAppReady();
    });

    it('should keep the news panel hidden by default', () => {
      // Based on my analysis of NewsPanel.tsx, the default state
      // for the panel is to be hidden with 'visibility: hidden'.
      // I confirm this is the initial state.
      cy.get('aside.max-w-md')
        .should('not.be.visible')
        .and('have.css', 'visibility', 'hidden');
    });

    it('should not show a loading indicator indefinitely', () => {
      // My custom command 'waitForAppReady' already waits for the spinner
      // to disappear. This test serves as a direct confirmation of that state.
      cy.get('.animate-spin-slow').should('not.exist');
    });
  });

  // I am testing the primary "happy path" for a user:
  // searching for a country and seeing the corresponding news.
  context('State with Data (Happy Path)', () => {

    it('should display news when the API responds with data', () => {
      // 1. I first intercept the news API call to provide a consistent
      //    dataset from my fixture file. This makes the test reliable.
      cy.intercept('GET', '/news_data.json', { 
        fixture: 'noticias-de-ejemplo.json' 
      }).as('getNews');

      // 2. I visit the page and wait for my mocked data to be loaded
      //    and for the initial loading overlay to disappear.
      cy.visit('/');
      cy.wait('@getNews');
      cy.waitForAppReady();
      
      // 3. I simulate a user searching for a country. I use the ARIA role
      //    for a robust selector and simulate pressing 'Enter'.
      cy.get('input[role="combobox"]')
        .should('be.visible')
        .type('Bulgaria{enter}');

      // 4. I verify that the panel is now visible.
      cy.get('aside.max-w-md')
        .should('be.visible')
        .and('not.have.css', 'visibility', 'hidden');

      // 5. I check that the panel contains the correct news content
      //    from my fixture file.
      cy.get('.flex-grow.overflow-y-auto')
        .should('be.visible')
        .within(() => {
          cy.contains('Bulgaria: Holocaust memorials').should('be.visible');
        });

      // 6. Finally, I confirm that the "no news available"
      //    message is not being displayed.
      cy.contains('No news available').should('not.exist');
    });
  });
});