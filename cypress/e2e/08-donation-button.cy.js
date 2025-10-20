// cypress/e2e/08-donation-button.cy.js

describe('Global News - Donation Button Functionality', () => {

  beforeEach(() => {
    // For these integration tests, I need to prevent the actual form submission
    // which would navigate away from the application.
    // I use cy.on('window:before:load') to reliably stub the form's submit method
    // before any application code runs, aliasing it as 'formSubmit'.
    cy.on('window:before:load', (win) => {
      cy.stub(win.HTMLFormElement.prototype, 'submit').as('formSubmit');
    });

    // I visit the page and wait for it to be fully ready.
    cy.visit('/');
    cy.waitForPageLoad();
    cy.waitForAppReady(); // My command waits for the loading overlay.
  });

  it('should open and close the donation dropdown correctly', () => {
    // I am testing the basic visibility toggle of the donation options.

    // 1. I find the main button using its title and click it.
    cy.get('button[title="Support project"]').click();

    // 2. I verify the dropdown panel is now visible by checking for its header.
    cy.contains('h4', 'Support this project').should('be.visible');

    // 3. I click the "Cancel" button to close it.
    cy.contains('button', 'Cancel').click();

    // 4. I verify the panel has disappeared.
    cy.contains('h4', 'Support this project').should('not.exist');
  });

  it('should prepare a PayPal submission when clicking a preset amount ($10)', () => {
    // I am verifying that clicking a preset amount constructs the correct
    // PayPal form data before the (stubbed) submission.

    // 1. I open the dropdown.
    cy.get('button[title="Support project"]').click();

    // 2. I click the '$10' button.
    cy.contains('button', '$10').click();

    // 3. I verify that my 'formSubmit' stub was called exactly once.
    cy.get('@formSubmit').should('be.calledOnce');

    // 4. I perform an advanced verification by inspecting the submitted form element.
    //    I access the form element using spy.getCall(0).thisValue.
    cy.get('@formSubmit').should((spy) => {
      const form = spy.getCall(0).thisValue; // Get the <form> element from the stub's context
      
      // I verify essential PayPal form attributes and hidden input values.
      expect(form.action).to.equal('https://www.paypal.com/donate');
      expect(form.target).to.equal('_blank');
      expect(form.elements.namedItem('amount').value).to.equal('10');
      expect(form.elements.namedItem('business').value).to.equal('omarviquezfalcon@gmail.com');
    });
  });

  it('should prepare a PayPal submission with a custom amount', () => {
    // I am testing the custom amount input and its corresponding submission.

    // 1. I open the dropdown.
    cy.get('button[title="Support project"]').click();

    // 2. I find the custom amount input, clear it, and type a new value.
    cy.get('input[type="number"]').clear().type('25');

    // 3. I click the "Donate" button associated with the custom amount.
    cy.contains('button', 'Donate').click();

    // 4. I verify the form submission was triggered.
    cy.get('@formSubmit').should('be.calledOnce');

    // 5. I inspect the submitted form to ensure the custom amount was used.
    cy.get('@formSubmit').should((spy) => {
      const form = spy.getCall(0).thisValue;
      expect(form.elements.namedItem('amount').value).to.equal('25');
    });
  });

  it('should disable the "Donate" button if the custom amount is invalid', () => {
    // I am testing the input validation logic for the custom amount.

    // 1. I open the dropdown.
    cy.get('button[title="Support project"]').click();

    // 2. I check the initial state: the button should be enabled with the default value.
    cy.contains('button', 'Donate').should('not.be.disabled');

    // 3. I clear the input. The button should become disabled.
    //    (Based on the `disabled={!customAmount...}` logic in DonationButton.tsx).
    cy.get('input[type="number"]').clear();
    cy.contains('button', 'Donate').should('be.disabled');

    // 4. I type "0". The button should remain disabled.
    //    (Based on the `parseFloat(customAmount) < 1` logic).
    cy.get('input[type="number"]').type('0');
    cy.contains('button', 'Donate').should('be.disabled');

    // 5. I type "1". The button should become enabled again.
    cy.get('input[type="number"]').clear().type('1');
    cy.contains('button', 'Donate').should('not.be.disabled');
  });

});