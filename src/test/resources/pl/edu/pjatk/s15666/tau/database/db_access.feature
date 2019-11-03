Feature: Database Access?
  Everybody wants to stress test postgres

  Background:
    Given object is created
    When object is added to the database

  Scenario: Object is persistent
    Then object can be read from the database

  Scenario: Object is removed
    Then object can be removed from the database