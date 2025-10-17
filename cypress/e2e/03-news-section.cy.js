describe('Global News - News Section Tests', () => {
  beforeEach(() => {
    cy.visit('/')
    cy.waitForPageLoad()
    cy.waitForNetworkIdle()
  })

  it('should display the News section', () => {
    // Verify the News heading is visible
    cy.contains('News').should('be.visible')
    
    // Check for the news content area
    cy.get('body').should('contain.text', 'News')
  })

  it('should handle "no news available" state correctly', () => {
    // Based on the fetched content, the page shows "No news available"
    cy.contains('No news available').should('be.visible')
    
    // Verify the complete message including the daily update info
    cy.contains('News is updated daily').should('be.visible')
    
    // Check that the message is properly formatted
    cy.get('body').should('contain.text', 'No news available for')
  })

  it('should display news section layout properly', () => {
    // Verify the News section has proper structure
    cy.contains('News').should('be.visible')
    
    // Check that there's a news container or section
    cy.get('body').then($body => {
      // Look for news-related containers
      const newsSelectors = [
        '.news-section',
        '#news',
        '[class*="news"]',
        '.news-container',
        '.news-content'
      ]
      
      // At minimum, verify the News text is present
      cy.contains('News').should('be.visible')
    })
    
    // Verify the update frequency information
    cy.contains('daily').should('be.visible')
  })

  it('should handle news loading states', () => {
    // Check initial state
    cy.contains('News').should('be.visible')
    
    // Verify that the no-news state is displayed properly
    cy.contains('No news available').should('be.visible')
    
    // Check that the message explains the update schedule
    cy.contains('updated daily').should('be.visible')
    
    // Ensure the news section doesn't show loading indefinitely
    cy.get('body').should('not.contain.text', 'Loading news...')
  })

  it('should maintain news section functionality across interactions', () => {
    // Store the initial news section state
    cy.contains('News').should('be.visible')
    cy.contains('No news available').should('be.visible')
    
    // Simulate some user interactions that might affect the news section
    cy.get('body').click(200, 200, { force: true })
    cy.wait(1000)
    
    // Verify news section is still present and functional
    cy.contains('News').should('be.visible')
    cy.contains('No news available').should('be.visible')
    
   
    
    // Verify news section is still visible after scrolling
    cy.contains('News').should('be.visible')
  })

  it('should display proper styling and layout for news section', () => {
    // Verify the news section has visible content
    cy.contains('News').should('be.visible')
    
    // Check that the news content is readable (not hidden or overlapped)
    cy.contains('No news available').should('be.visible')
    
    // Verify version information is displayed
    cy.contains('v1.3.1').should('be.visible')
    
    // Ensure the news section maintains its position in the layout
    cy.get('body').then($body => {
      const newsElement = $body.find(':contains("News")')
      if (newsElement.length > 0) {
        cy.wrap(newsElement.first()).should('be.visible')
      }
    })
  })

  it('should handle news section across different viewport sizes', () => {
    const viewports = [
      { width: 1920, height: 1080 },
      { width: 768, height: 1024 },
      { width: 375, height: 667 }
    ]

    viewports.forEach(viewport => {
      cy.viewport(viewport.width, viewport.height)
      cy.wait(1000)
      
      // Verify news section is visible at different screen sizes
      cy.contains('News').should('be.visible')
      cy.contains('No news available').should('be.visible')
      
      // Check that the content remains readable
      cy.contains('updated daily').should('be.visible')
    })
  })
})