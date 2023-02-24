Feature: Application Approval

  User Story: As a loan processing agent, I need to submit a loan applicant to the credit agency
  for processing so that I can either approve or reject the loan applicant.

  Business Rules:
  Credit Scores of 720 and over will be approved.
  Credit Scores lower than 720 will not be approved.

  Acceptance Criteria:
  Successfully approve applications with credit scores of 720 and higher.
  Successfully decline applications with credit scores lower than 720.
  Receive an error if there was a processing problem.

  Scenario: Score of 800 should get approved
    Given I am an applicant with SSN "5255555555"
    When My credit score is 800
    And I submit the application
    Then My application should be "approved"

  Scenario: Score of 500
    Given I am an applicant with SSN "222222222"
    When My credit score is 500
    And I submit the application
    Then My application should be "declined"

  Scenario: Score of 720
    Given I am an applicant with SSN "333333331"
    When My credit score is 720
    And I submit the application
    Then My application should be "approved"


  Scenario Outline: SSN of <SSN> and credit score of <score> get <result>
    Given I am an applicant with SSN <SSN>
    When My credit score is <score>
    And I submit the application
    Then My application should be <result>

  Examples:
    |     SSN     | score |   result   |
    | "111111111" |  700  | "declined" |
    | "111111112" |  720  | "approved" |