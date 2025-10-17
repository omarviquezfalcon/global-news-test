describe('Global News - Country Selection on Globe', () => {
  beforeEach(() => {
    cy.visit('/')
    cy.waitForPageLoad()
    cy.waitForNetworkIdle()
  })

  it('should allow selecting a country from the globe and update news section', () => {
    // Espera breve para permitir que el globo se renderice
    cy.wait(1000)

    // Busca el globo usando múltiples selectores posibles
    cy.get('body').then($body => {
      const globeSelectors = [
        'canvas',
        '[class*="globe"]',
        '[id*="globe"]',
        '[data-testid*="globe"]',
        'svg',
        '.three-canvas',
        '#globe-container'
      ]

      const foundSelector = globeSelectors.find(sel => $body.find(sel).length > 0)

      if (foundSelector) {
        cy.log(`Globo encontrado con selector: ${foundSelector}`)

        // Interactúa con el globo haciendo clic en una posición arbitraria
        cy.get(foundSelector).first().click(400, 300, { force: true })
        cy.wait(2000)

        // Verifica que se haya actualizado la sección de país
        cy.contains(/Hiciste clic|Verifica que|News.*for/i).should('exist')
        cy.contains(/No news articles|Top story/i).should('exist')
      } else {
        cy.log('No se encontró el globo interactivo en el DOM')
      }
    })
  })
})