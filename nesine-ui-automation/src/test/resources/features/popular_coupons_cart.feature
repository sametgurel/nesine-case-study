@ui @popular_coupons @cart
Feature: Nesine Popular Coupons and Betslip Retention

  As a Nesine user
  I want to select a popular coupon, add it to my betslip, and verify its event details
  And ensure the betslip remains intact while navigating across key header pages

  @case_study @smoke
  Scenario: Select popular coupon, verify betslip event details, and verify betslip retention across header pages
    Given the user is on the Nesine homepage
    When the user clicks the Popüler Kuponlar button
    Then the Popüler Kuponlar page should be displayed
    When the user selects any coupon from the list and clicks Hemen Oyna to add to betslip
    Then the betslip should open and display the added coupon
    And the added coupon event names should be displayed correctly in the betslip
    And the added coupon event dates should be displayed correctly in the betslip
    Then the user navigates through header pages sequentially verifying each page opens and the betslip is preserved:
      | İddaa         |
      | Canlı Sonuçlar |
      | Dakika Bahis  |
