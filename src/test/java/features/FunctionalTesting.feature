@RestfulBookerPlatform
Feature: Restful Booker Platform critical functionalities

  Background: To verify Restful Booker Platform site is up and accessible
    Given user able to access the site
    When user gets title as "Restful-booker-platform demo"
    Then verify homepage is displayed

  @scenario1
  Scenario: verify admin panel is accessible with given credentials
    And click on the "admin panel" link from homepage
    Then login to the account using username and password
    And logout from the admin page

  @scenario2
  Scenario: verify admin is able to create a room from admin panel
    And click on the "admin panel" link from homepage
    Then login to the account using username and password
    When user enters below details to create a room and then room created
    | Room     | Type   | Accessible | Price | RoomDetails |
    | TestRoom | Twin   | true       | 100   | WiFi,TV     |

  @scenario3
  Scenario: verify user is able to submit form from home page
    And scroll to the form and update the below fields
      | Name | Email           | Phone        | Subject   | Message                |
      | Test | Test@gmail.com  | 111111111111 | Test mail | This is a test message |
    Then submit the form and validate the success message "Thanks for getting in touch"

  @scenario4
  Scenario: verify user is able to Book a room from home page
    And scroll to the Rooms and check "room101" is displayed
    Then click on "Book this room" button for the room "room101"
    When user enter below details for booking
      | Firstname | Lastname | Email          | Phone        | StartDate   | EndDate     |
      | Test      | Test     | Test@gmail.com | 222222222222 | 05,May 2025 | 06,May 2025 |
    Then verify "Booking Successful!" message on page

