Feature: As I user, I need to validate the clue API

  Background:
    * print 'URL: ', api.uri + '/clue/'
    * url api.uri

  Scenario: Detectar una pista en un manuscrito válido
    Given path '/clue'
    And request
    """
    {
      "manuscript": [
        "RRRRQQ",
        "XRLQRE",
        "NARURR",
        "REMRAL",
        "EGSILE",
        "BRINDS"
      ]
    }
    """
    When method POST
    * print response
    Then status 200
    And match response.hasClue == true
    And match response.character == 'R'
    And match response.direction == '#string'
    And match response.from == '#string'
    And match response.to == '#string'

  Scenario: Manuscrito sin pista
    Given path '/clue'
    And request
    """
    {
      "manuscript": [
        "ABCDER",
        "TQWERT",
        "ZXCVDG",
        "POIUYT",
        "LKJHGF",
        "MNBVCR"
      ]
    }
    """
    When method POST
    Then status 403
    And match response.hasClue == false
    And match response.message == "No se encontró ninguna pista en el manuscrito."

  Scenario: Validar error por entrada inválida
    Given path '/clue'
    And request { "manuscript": [] }
    When method POST
    Then status 400

  Scenario: Validar estructura basica del JSON
    Given path '/clue'
    And request
    """
    {
      "manuscript": [
        "RTHGQW",
        "XRLQRE",
        "NARURR",
        "REMRAL",
        "EGSILE",
        "BRINDS"
      ]
    }
    """
    When method POST
    Then status 200
    And match response ==
    """
     {
      hasClue: true,
      character: '#string',
      direction: '#string',
      from: '#string',
      to: '#string'
     }
    """