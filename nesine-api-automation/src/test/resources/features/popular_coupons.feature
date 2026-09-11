@regression
Feature: Nesine.com Popular Coupons REST API Test Suite
  As a quality assurance engineer
  I want to verify the PopularCoupons API endpoint
  So that data consistency, schema integrity, query filtering, and model deserialization are guaranteed.

  Background:
    Given the Nesine API specification is initialized

  # =========================================================================================
  # 1. TEMEL KONTROLLER
  # =========================================================================================
  @smoke @basic
  Scenario: 1.1 Verify Popular Coupons endpoint returns HTTP 200 and successful status
    When the client sends a GET request to "/PopularCoupons" with query parameter "eventCount" as "0"
    Then the HTTP status code should be 200
    And the response body field "sc" should be 200
    And the popular coupon list "d" should not be empty

  # =========================================================================================
  # 2. VERİ TUTARLILIĞI
  # =========================================================================================
  @consistency
  Scenario: 2.1 Verify coupon eventCount matches the actual size of the events list
    When the client sends a GET request to "/PopularCoupons" with query parameter "eventCount" as "0"
    Then the HTTP status code should be 200
    And each coupon eventCount value must strictly equal the number of items in its events list

  @consistency
  Scenario: 2.2 Verify minimum odd is not greater than maximum odd for every event
    When the client sends a GET request to "/PopularCoupons" with query parameter "eventCount" as "0"
    Then the HTTP status code should be 200
    And for all events across all coupons minOdd must be less than or equal to maxOdd

  @consistency
  Scenario: 2.3 Verify event type is consistent with sportId
    When the client sends a GET request to "/PopularCoupons" with query parameter "eventCount" as "0"
    Then the HTTP status code should be 200
    And each event type must be consistent with its assigned sportId

  @consistency
  Scenario: 2.4 Verify coupons are sorted in ascending order by orderIndex
    When the client sends a GET request to "/PopularCoupons" with query parameter "eventCount" as "0"
    Then the HTTP status code should be 200
    And coupons should be ordered by orderIndex in ascending order

  # =========================================================================================
  # 3. QUERY PARAMETER KONTROLLERİ
  # =========================================================================================
  @query-param
  Scenario Outline: 3.1 Verify filtering popular coupons by valid eventCount parameter
    When the client sends a GET request to "/PopularCoupons" with query parameter "eventCount" as "<count>"
    Then the HTTP status code should be 200
    And the response body field "sc" should be 200
    And all coupons in the response should contain exactly <count> events

    Examples:
      | count |
      | 1     |
      | 2     |

  @query-param
  Scenario: 3.2 Verify endpoint behavior when eventCount query parameter is omitted
    When the client sends a GET request to "/PopularCoupons" without any query parameters
    Then the HTTP status code should be 200
    And the response body field "sc" should be 200
    And the popular coupon list "d" should not be empty

  @query-param @negative
  Scenario: 3.3 Verify negative eventCount parameter returns business validation error
    When the client sends a GET request to "/PopularCoupons" with query parameter "eventCount" as "-1"
    Then the HTTP status code should be 200
    And the response body field "sc" should be 400
    And the response error list "el" should contain error code 101 with an invalid parameter message

  @query-param @negative
  Scenario: 3.4 Verify non-numeric invalid eventCount parameter returns HTTP 400 Bad Request
    When the client sends a GET request to "/PopularCoupons" with query parameter "eventCount" as "invalid_str"
    Then the HTTP status code should be 400

  # =========================================================================================
  # 4. JSON SCHEMA VALIDATION
  # =========================================================================================
  @schema
  Scenario: 4.1 Verify the complete response adheres to the defined JSON schema
    When the client sends a GET request to "/PopularCoupons" with query parameter "eventCount" as "0"
    Then the HTTP status code should be 200
    And the response body must conform to JSON schema "schemas/popular_coupons_schema.json"

  # =========================================================================================
  # 5. POJO DESERIALIZATION
  # =========================================================================================
  @pojo
  Scenario: 5.1 Verify response successfully deserializes into POJO models without type errors
    When the client sends a GET request to "/PopularCoupons" with query parameter "eventCount" as "0"
    Then the HTTP status code should be 200
    And the response can be mapped to the "PopularCouponsResponse" POJO class
    And the deserialized POJO attributes should contain valid non-null coupon data

  # =========================================================================================
  # 6. SERBEST SENARYOLAR
  # =========================================================================================
  @custom @performance
  Scenario: 6.1 Custom Scenario 1 - Verify API response time meets SLA threshold
    When the client sends a GET request to "/PopularCoupons" with query parameter "eventCount" as "0"
    Then the HTTP status code should be 200
    And the response time should be within the SLA threshold of 2500 milliseconds

  @custom @security
  Scenario: 6.2 Custom Scenario 2 - Verify security and content type headers
    When the client sends a GET request to "/PopularCoupons" with query parameter "eventCount" as "0"
    Then the HTTP status code should be 200
    And the response header "Content-Type" should contain "application/json"
    And the response header "Strict-Transport-Security" should be present

  @custom @integrity
  Scenario: 6.3 Custom Scenario 3 - Verify coupon hash format and event outcomes completeness
    When the client sends a GET request to "/PopularCoupons" with query parameter "eventCount" as "0"
    Then the HTTP status code should be 200
    And all coupons must have a valid 32 character hex couponHash
    And every event in each coupon must have at least one outcome with a positive odd value
