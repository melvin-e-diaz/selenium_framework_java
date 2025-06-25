Feature: LoginFeature
  This feature is responsible for testing all the scenarios for Login of application

  Scenario: Check Login with correct username and password
    And I ensure application opened
    Then I click login link
    When I enter UserName and Password
      | UserName | Password |
      | admin    | password |
    Then I click login button
    Then I should see the username with hello