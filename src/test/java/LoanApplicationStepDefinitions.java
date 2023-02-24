package test.java;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import prodCode.CreditAgency;
import prodCode.LoanAgent;

public class LoanApplicationStepDefinitions {
    LoanAgent agent = new LoanAgent();
    CreditAgencyTestingStub agency = new CreditAgencyTestingStub();
    String ssn;
    int creditScore;
    boolean result;

    @Given("I am an applicant with SSN {string}")
    public void i_am_an_applicant_with_ssn(String SSN) throws Throwable {
        ssn = SSN;
    }

    @When("My credit score is {int}")
    public void my_credit_score_is(int cScore) throws Throwable {
        creditScore = cScore;
        agency.setCreditScore(cScore);
        agent.setCreditAgency(agency);

    }

    @When("I submit the application")
    public void i_submit_the_application() throws Throwable {
        result = agent.processLoan(ssn);
    }

    @Then("My application should be {string}")
    public void my_application_should_be(String status) throws Throwable {
        agent.setCreditAgency(agency);
        try {
            boolean actualResult = agent.processLoan(ssn);
            if (status.equalsIgnoreCase("approved"))
                Assert.assertTrue(actualResult);
            else
                Assert.assertFalse(actualResult);
        }
        catch(AssertionError e) {
            throw new Exception("Application processing test failed");
        }

    }

    @Then("I should receive an error")
    public void i_should_receive_an_error() throws Throwable {
        Assert.assertTrue(!agent.getErrorLog().isEmpty());
    }
}
