Feature: Flight Reservation using BlazeDemo

  Scenario: Book lowest priced flight successfully
    Given User opens BlazeDemo website
    When User selects departure city
    And User selects destination city
    And User clicks Find Flights
    Then Available flights should be displayed
    When User selects lowest priced flight
    And User enters passenger details
    And User clicks Purchase Flight
    Then Booking confirmation page should be displayed