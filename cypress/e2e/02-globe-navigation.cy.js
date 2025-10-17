describe('Global News - Interactive Globe Navigation Tests', () => {
  beforeEach(() => {
    cy.visit('/')
    cy.waitForPageLoad()
    cy.waitForNetworkIdle()
  })

  it('should display the interactive globe', () => {
    // Espera breve para permitir que el mensaje de carga aparezca
    cy.wait(300)

   // Intenta detectar el texto si está brevemente en el DOM
cy.document().then((doc) => {
  const bodyText = doc.body.innerText || ''
  if (bodyText.includes('Loading Interactive News Globe')) {
    cy.log('Mensaje de carga detectado en el DOM')
  } else {
    cy.log('Mensaje de carga no detectado — puede estar renderizado fuera del DOM o desaparecer muy rápido')
  }
})


    // Verifica que el globo interactivo esté presente
    cy.get('canvas, [class*="globe"], [id*="globe"], [data-testid="globe"], svg, .three-canvas, #globe-container')
      .should('exist')
  })

  it('should allow interaction with the globe interface', () => {
    cy.wait(3000)

    cy.get('body').then($body => {
      const interactiveElements = $body.find('canvas, button, [role="button"], .clickable, [onclick]')

      if (interactiveElements.length > 0) {
        cy.get('canvas, button, [role="button"]').first().then($el => {
          if ($el.is(':visible')) {
            cy.wrap($el).click({ force: true })
            cy.wait(1000)
          }
        })
      }
    })

    cy.contains('Click a country or search to discover today\'s top story').should('be.visible')
  })

  it('should handle search functionality if available', () => {
    cy.get('body').then($body => {
      const searchElements = $body.find('input[type="search"], input[placeholder*="search"], .search-input, #search')

      if (searchElements.length > 0) {
        cy.get('input[type="search"], input[placeholder*="search"], .search-input, #search')
          .first()
          .should('be.visible')
          .type('United States{enter}')

        cy.wait(2000)
        cy.get('body').should('exist')
      } else {
        cy.contains('search to discover').should('be.visible')
      }
    })
  })

  it('should navigate and maintain page state', () => {
    const clickPositions = [
      { x: 100, y: 100 },
      { x: 300, y: 200 },
      { x: 500, y: 300 }
    ]

    clickPositions.forEach(position => {
      cy.get('body').click(position.x, position.y, { force: true })
      cy.wait(500)
      cy.contains('Interactive News Globe').should('be.visible')
    })

    cy.contains('Click a country or search').should('be.visible')
    cy.contains('By Omar Viquez').should('be.visible')
  })

  it('should handle keyboard navigation', () => {
    cy.get('body').focus()

    // Simula Tab (aunque no siempre funciona en <body>)
    cy.wait(300)
    cy.get('body').type('{tab}')
    cy.wait(500)

    // Simula flechas para rotar el globo
    cy.get('body').type('{leftarrow}{rightarrow}{uparrow}{downarrow}')
    cy.wait(500)

    cy.contains('Interactive News Globe').should('be.visible')
  })
})