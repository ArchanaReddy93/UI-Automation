@NonFunctionalTesting
Feature: Restful Booker Platform test scenarios for security testing

  Background: To verify Restful Booker Platform site is up and accessible
    Given user able to access the site
    When user gets title as "Restful-booker-platform demo"
    Then verify homepage is displayed

  @securityTesting
  Scenario: verify login should be unsuccessful with invalid credentials for admin panel
    And click on the "admin panel" link from homepage
    Then verify user is not able to login with invalid input
      | Username | Password |
      | Test     | Password |

  @usabilityTesting
  Scenario: verify elements like buttons, links are visible, accessible and responsive from homepage.
    And check all links present on homepage are accessible and responsive
      | Link1                               | Link2                          | Link3                                       | Link4     | Link5       | Link6                             | Link7                      |
      | restful-booker-platform source code | restful-booker-platform source | build process in this public build pipeline | home page | admin panel | read more about the features here | feel free to raise it here |
