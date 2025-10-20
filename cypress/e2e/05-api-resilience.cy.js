// cypress/e2e/05-api-resilience.cy.js

describe('Global News - API Resilience and Error Handling', () => {

  it('should display "no news available" upon search if the API fails (500 Error)', () => {
    // I am testing the application's behavior when a critical backend
    // service fails. The user should see a graceful message, not a broken page.

    // 1. I intercept the API request and force a 500 server error response.
    cy.intercept('GET', '/news_data.json', { 
      statusCode: 500,
      body: { message: 'Internal Server Error' }
    }).as('getNewsFail');

    // 2. I visit the page and wait for the app to handle the failed request.
    cy.visit('/');
    cy.wait('@getNewsFail');
    cy.waitForAppReady(); // I wait for the loading overlay to disappear.

    // 3. I confirm the initial state is correct: the panel is hidden.
    cy.get('aside.max-w-md').should('not.be.visible');

    // 4. I simulate a user interaction by searching for a valid country.
    cy.get('input[role="combobox"]').type('Mexico{enter}');

    // 5. I verify that the panel opens as expected.
    cy.get('aside.max-w-md', { timeout: 10000 })
      .should('be.visible')
      .and('not.have.css', 'visibility', 'hidden');

    // 6. I verify the crucial outcome: the panel shows the "no news" message
    //    because the internal news cache is empty due to the API failure.
    cy.get('aside.max-w-md')
      .should('contain.text', 'No news available for Mexico')
      .and('contain.text', 'News is updated daily');
  });

  it('should display "no news available" upon search if the API returns empty data', () => {
    // This test ensures the app correctly handles a successful API response
    // that simply contains no data.

    // 1. I intercept the API and return an empty JSON object.
    cy.intercept('GET', '/news_data.json', { 
      statusCode: 200,
      body: {} // A successful response, but with no news items.
    }).as('getNewsEmpty');

    // 2. I visit the page and wait for the app to process the empty data.
    cy.visit('/');
    cy.wait('@getNewsEmpty');
    cy.waitForAppReady();

    // 3. I confirm the initial state is correct: the panel is hidden.
    cy.get('aside.max-w-md').should('not.be.visible');

    // 4. I simulate a user searching for a valid country.
    cy.get('input[role="combobox"]').type('Mexico{enter}');

    // 5. I verify the panel opens as expected.
    cy.get('aside.max-w-md').should('be.visible');

    // 6. I verify the content: it should display the standard "no news" message.
    cy.get('aside.max-w-md')
      .should('contain.text', 'No news available for Mexico');
  });

});