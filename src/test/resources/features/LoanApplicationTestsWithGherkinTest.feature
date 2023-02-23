Feature: Application Approval

  User Story: As a Loan Agent, I want to get accurate loan approvals for my applications so that we finance qualified customers.

  Business Rules:
  Credit Scores of 720 and over will be approved.
  Credit Scores lower than 720 will not be approved.

  Acceptance Criteria:
  Successfully approve applications with credit scores of 720 and higher.
  Successfully decline applications with credit scores lower than 720.

  Scenario: Score of 800
    Given I am an applicant with SSN "5255555555"
    When I apply for a loan with 800 credit score
    Then My application should be "approved"

  Scenario: Score of 500
    Given I am an applicant with SSN "222222222"
    When I apply for a loan with 500 credit score
    Then My application should be "declined"
