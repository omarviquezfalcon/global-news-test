describe('Global News - Page Load Tests', () => {
  beforeEach(() => {
    // Visit the main page
    cy.visit('/')
  })

  it('should load the main page successfully', () => {
    // Check that the page loads and has the correct title
    cy.title().should('contain', 'Interactive News Globe')
    
    // Verify the main heading is visible
    cy.contains('Interactive News Globe').should('be.visible')
    
    // Check the subtitle/description
    cy.contains('Click a country or search to discover today\'s top story')
      .should('be.visible')
    
    // Verify author information
    cy.contains('By Omar Viquez').should('be.visible')
    
    // Check that the loading message appears
    cy.contains('Loading Interactive News Globe...').should('be.visible')
    
    // Wait for the page to be fully loaded
    cy.waitForPageLoad()
  })

  it('should display all main page elements', () => {
    // Check for the presence of main sections
    cy.get('body').should('be.visible')
    
    // Verify the globe container or canvas exists
    cy.get('canvas, [class*="globe"], [id*="globe"], [data-testid="globe"]', { timeout: 15000 })
      .should('exist')
    
    // Check for News section
    cy.contains('News').should('be.visible')
    
    // Verify version information
    cy.contains('v1.3.1').should('be.visible')
    
    // Check that interactive elements are present
    cy.get('body').should('contain.text', 'Interactive News Globe')
  })

  it('should handle page responsiveness', () => {
    // Test different viewport sizes
    const viewports = [
      { width: 1920, height: 1080, device: 'desktop' },
      { width: 768, height: 1024, device: 'tablet' },
      { width: 375, height: 667, device: 'mobile' }
    ]

    viewports.forEach(viewport => {
      cy.viewport(viewport.width, viewport.height)
      
      // Verify main content is still visible at different sizes
      cy.contains('Interactive News Globe').should('be.visible')
      cy.contains('Click a country or search').should('be.visible')
      
      // Wait a moment for layout adjustment
      cy.wait(1000)
    })
  })
})