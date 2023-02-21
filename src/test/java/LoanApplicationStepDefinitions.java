import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class LoanApplicationStepDefinitions {
    LoanAgent agent = new LoanAgent();
    CreditAgencyTestingStub agency = new CreditAgencyTestingStub();
    String ssn;
    int creditScore;

    @Given("I am an applicant with SSN {string}")
    public void i_am_an_applicant_with_ssn(String SSN) throws Throwable {
        ssn = SSN;
    }

    @When("I apply for a loan with {int} credit score")
    public void i_apply_for_a_loan_with_credit_score(int cScore) throws Throwable {
        creditScore = cScore;
        agency.setCreditScore(cScore);
    }

    @Then("My application should be {string}")
    public void my_application_should_be(String status) throws Throwable {
        agent.setCreditAgency(agency);
        boolean actualResult = agent.processLoan(ssn);
        if (status.equalsIgnoreCase("approved"))
            Assert.assertTrue(actualResult);
        else
            Assert.assertFalse(actualResult);

    }
}
