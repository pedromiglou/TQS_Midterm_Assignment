Feature: Midterm Assignment Webpage

  Scenario: Obtain the air quality of North Pole, Alaska, USA
    When I navigate to 'http://localhost:4200/'
    And I select the country 'USA'
    And I select the state 'Alaska'
    And I select the city 'North Pole'
    And I click the button to get the air quality
    Then I should be shown the 'Humidity'
    And I should be shown the 'US Air Quality Index'
    And I should be shown the 'Main pollutant for US AQI'
    And I should be shown the 'Chinese Air Quality Index'
    And I should be shown the 'Main pollutant for Chinese AQI'

  Scenario: Obtain the API statistics
    When I navigate to 'http://localhost:4200/'
    And I click the button to get the statistics
    Then I should be shown the API 'Count'
    And I should be shown the API 'Hits'
    And I should be shown the API 'Misses'
