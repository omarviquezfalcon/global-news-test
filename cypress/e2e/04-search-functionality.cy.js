// cypress/e2e/04-search-functionality.cy.js

describe('Global News - Advanced Search Functionality', () => {

  // Before each test, I ensure a predictable data state by intercepting
  // the news API to use my fixture data. I also wait for the app
  // to be fully interactive.
  beforeEach(() => {
    // I intercept the news request for control and consistency.
    cy.intercept('GET', '/news_data.json', { 
      fixture: 'noticias-de-ejemplo.json' 
    }).as('getNews');

    cy.visit('/');
    cy.wait('@getNews'); // I wait for the mock data to be fetched.
    cy.waitForAppReady(); // I wait for the loading overlay to disappear.
  });

  it('should allow navigation and selection with arrow keys and Enter', () => {
    // Here, I'm testing the full keyboard accessibility flow.

    // I get the search input by its ARIA role and start typing.
    cy.get('input[role="combobox"]').type('Bulg');

    // I verify that the suggestion listbox is visible.
    cy.get('ul[role="listbox"]').should('be.visible');

    // I simulate pressing 'ArrowDown' to highlight the first item.
    cy.get('input[role="combobox"]').type('{downarrow}');

    // I verify the first item now has the 'selected' class.
    // (I identified this class from the SearchBar.tsx component code).
    cy.get('li[role="option"]').first().should('have.class', 'bg-green-600/70');

    // I simulate pressing 'Enter' to confirm the selection.
    cy.get('input[role="combobox"]').type('{enter}');

    // Finally, I verify that the news panel opened with the correct content.
    cy.get('aside.max-w-md').should('be.visible').and('contain', 'Bulgaria');
  });

  it('should allow selecting a country by clicking a suggestion', () => {
    // This test covers the primary mouse user flow.

    // I type into the search input.
    cy.get('input[role="combobox"]').type('Bulg');

    // Instead of using the keyboard, I click the suggestion item directly.
    cy.get('li[role="option"]').contains('Bulgaria').click();

    // I verify that the news panel opened correctly.
    cy.get('aside.max-w-md').should('be.visible').and('contain', 'Bulgaria');
  });

  it('should not display the suggestion list for an invalid search', () => {
    // This is an edge case test for a country that does not exist.

    // I type a search term that I know will not return results.
    cy.get('input[role="combobox"]').type('Atlantis');

    // I verify that the suggestion list (the <ul> element) does not exist in the DOM.
    cy.get('ul[role="listbox"]').should('not.exist');
  });

  it('should close the suggestion list when pressing Escape', () => {
    // I am testing the 'Escape' key logic I found in SearchBar.tsx.

    // I first trigger the suggestion list to appear.
    cy.get('input[role="combobox"]').type('Bulg');
    cy.get('ul[role="listbox"]').should('be.visible');

    // I then simulate the 'Escape' key press.
    cy.get('input[role="combobox"]').type('{esc}');

    // I verify that the list is now hidden.
    // (In SearchBar.tsx, the 'isOpen' state becomes false, so the <ul> is unmounted).
    cy.get('ul[role="listbox"]').should('not.exist');
  });

});