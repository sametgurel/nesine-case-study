@ui @popular_coupons @filters @extended
Feature: Popular Coupons Filters and Betslip Management (Serbest Senaryolar)

  As a Nesine user
  I want to switch tabs on popular coupons page
  And manage my betslip by clearing added coupons

  @free_scenario_1 @filtering
  Scenario Outline: Switch tabs on Popular Coupons page and verify coupon cards
    Given the user is on the Nesine homepage
    When the user clicks the Popüler Kuponlar button
    Then the Popüler Kuponlar page should be displayed
    When the user filters coupons by "<FilterOption>"
    Then the popular coupon list should be visible and not empty

    Examples:
      | FilterOption |
      | Hemen Oyna   |
      | Kazananlar   |

  @free_scenario_2 @betslip_management
  Scenario: Add coupon to betslip and clear it completely
    Given the user is on the Nesine homepage
    When the user clicks the Popüler Kuponlar button
    Then the Popüler Kuponlar page should be displayed
    When the user selects any coupon from the list and clicks Hemen Oyna to add to betslip
    Then the betslip should open and display the added coupon
    When the user clears all events from the betslip
    Then the betslip should be empty
