import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//Note:  This test suite does not address the requirement to write to the Error Log in the event of an invalid credit score


public class LoanApplicationTestsUsingStubObject {
	int testCreditScore;
	LoanAgent agent;
	CreditAgencyTestingStub creditAgency;
	
	@Before
	public void setUp()  {
		agent = new LoanAgent();
		creditAgency = new CreditAgencyTestingStub();
		agent.setCreditAgency(creditAgency);
	}
	
	@After
	public void teardown()  {
		agent = null;
		creditAgency = null;
	}

	@Test
	public void testWithLowCreditScore()  {
		testCreditScore = 719;
		String ssn = "123-45-6789";
		creditAgency.setCreditScore(testCreditScore);
		
		boolean result = agent.processLoan(ssn);
		Assert.assertFalse(result);	
	}
	
	@Test
	public void testWithBorderLineCreditScore()  {
		testCreditScore = 720;
		creditAgency.setCreditScore(testCreditScore);
		String ssn = "123-45-6789";
		
		boolean result = agent.processLoan(ssn);
		Assert.assertTrue(result);	
	}
	
	@Test
	public void testWithGoodCreditScore()  {
		testCreditScore = 721;
		creditAgency.setCreditScore(testCreditScore);
		String ssn = "123-45-6789";
		
		boolean result = agent.processLoan(ssn);
		Assert.assertTrue(result);	
	}
}
