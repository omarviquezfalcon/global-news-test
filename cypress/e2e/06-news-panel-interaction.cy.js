// cypress/e2e/06-news-panel-interaction.cy.js

describe('Global News - News Panel Interaction', () => {

  // Before each test in this suite, I need the news panel
  // to be open and display predictable content.
  beforeEach(() => {
    // 1. I ensure consistent data by intercepting the news API
    //    and using my fixture file.
    cy.intercept('GET', '/news_data.json', { 
      fixture: 'noticias-de-ejemplo.json' 
    }).as('getNews');

    // 2. I visit the page and wait for everything to load,
    //    including the mock data and the initial UI state.
    cy.visit('/');
    cy.wait('@getNews');
    cy.waitForAppReady();
  
    // 3. I open the panel by searching for a country I know
    //    is in my fixture data.
    cy.get('input[role="combobox"]').type('Bulgaria{enter}');

    // 4. I double-check that the panel is indeed open and displaying
    //    the expected content before proceeding with the actual test.
    cy.get('aside.max-w-md')
      .should('be.visible')
      .and('contain', 'Bulgaria'); // Using country name as a quick content check
  });

  it('should close the panel when clicking the "X" button', () => {
    // The panel is already open thanks to the beforeEach hook.
    // I am testing the primary method for closing the panel.

    // 1. I find the close button using its 'aria-label',
    //    which I identified as a robust selector in NewsPanel.tsx.
    cy.get('button[aria-label="Close news panel"]').click();

    // 2. I verify that the panel is now hidden.
    cy.get('aside.max-w-md')
      .should('not.be.visible')
      .and('have.css', 'visibility', 'hidden');
    
    // 3. As an additional check, I verify that the initial welcome text
    //    is visible again, confirming the UI returned to its default state.
    cy.contains("Click a country or search").should('be.visible');
  });

  it('should close the panel when pressing the "Escape" key', () => {
    // I am testing the keyboard accessibility feature for closing the panel,
    // based on the event listener I saw in NewsPanel.tsx.
    // The panel is already open.

    // 1. I simulate pressing the 'Escape' key on the page body.
    cy.get('body').type('{esc}');

    // 2. I verify that the panel closed successfully.
    cy.get('aside.max-w-md')
      .should('not.be.visible')
      .and('have.css', 'visibility', 'hidden');
  });

  it('should have "Read Full Story" links that open in a new tab', () => {
    // The panel is open and loaded with my fixture data.
    // I need to ensure external links behave correctly for the user.

    // 1. I find the "Read Full Story" link within the panel.
    const link = cy.get('aside.max-w-md').contains('Read Full Story');

    // 2. I verify the 'target' and 'rel' attributes, which are crucial
    //    for security and user experience when opening external links.
    //    I confirmed these attributes in the NewsPanel.tsx code.
    link.should('have.attr', 'target', '_blank');
    link.should('have.attr', 'rel', 'noopener noreferrer');

    // 3. I also verify that the link's 'href' attribute is not empty
    //    and contains a valid protocol identifier.
    link.should('have.attr', 'href')
        .and('include', 'https://'); // I consider 'https://' a good practice check.
  });

});