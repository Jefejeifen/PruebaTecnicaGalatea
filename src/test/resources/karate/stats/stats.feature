Feature: As I user, I need to validate the clue API

  Background:
    * print 'URL: ', api.uri + '/stats'
    * url api.uri

  Scenario: Validar estructura de respuesta
    Given path '/stats'
    When method GET
    * print response
    Then status 200
    And match response ==
    """
    {
      count_clue_found: '#number',
      count_no_clue: '#number',
      ratio: '#number'
    }
    """

  Scenario: Validar consistencia del ratio
    Given path '/stats'
    When method GET
    Then status 200
    * def found = response.count_clue_found
    * def notFound = response.count_no_clue
    * def ratio = response.ratio
    * print 'Real Ratio', ratio
    * def expectedRatio = (found + notFound == 0) ? 0 : (found / (found + notFound))
    * print 'Expected Ratio', expectedRatio
    And match ratio == expectedRatio
