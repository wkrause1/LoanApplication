import static org.mockito.Mockito.times;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;  
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

//   Mockito Notes
//	1.  We used both the Mockito.mock(some.class) approach and the @Mock annotation to mock objects in this test suite
//	2.  The CreditAgency is built using Mockito.mock() technique
//	3.  The ErrorLog is created with @Mock
//	4.  We are only verifying that something was written into the ErrorLog if an invalid credit score is returned.  
//	We do not verify that the correct message was written.  We did this so that we could demonstrate the verify() functionality
//	5.  This approach also allowed us to demonstrate the use of anyString().

//  Coding notes:
//  These tests were built using the following test coding practices:
//	1.  All test data is explicitly declared.  
//	2.  Test data is not some magic number passed as a parameter leaving one to wonder where it came from
//	3.  The test (function) name tells you exactly what is being tested
//	4.  The tests have been re-factored to comply with the DRY principle
//	5.  The tests demonstrate the use of both @Before and @After

public class LoanApplicationTestsWithMockito {

	LoanAgent agent;
	CreditAgency agency;
	
	@Mock
	ErrorLog errorLog;
	
	String lowCreditScoreSSN = "123-45-6789";
	String goodCreditScoreSSN = "987-65-4321";
	String borderLineCreditScoreSSN = "102-93-4875";
	String belowLimitCreditScoreSSN = "111-11-1111";
	String aboveLimitCreditScoreSSN = "99-999-9999";
	
	int lowCreditScore = 719;
	int borderLineCreditScore = 720;
	int goodCreditScore = 721;
	int belowLimitCreditScore = 199;
	int aboveLimitCreditScore = 851;
	
	//  Note:  We could have set the thenReturn value individually in each test rather than all at once in the @Before function
	@Before
	public void setup()  {
		agent = new LoanAgent();
		agency = Mockito.mock(CreditAgency.class);
		Mockito.when(agency.getScore(lowCreditScoreSSN)).thenReturn(lowCreditScore);
		Mockito.when(agency.getScore(goodCreditScoreSSN)).thenReturn(goodCreditScore);
		Mockito.when(agency.getScore(borderLineCreditScoreSSN)).thenReturn(borderLineCreditScore);
		Mockito.when(agency.getScore(belowLimitCreditScoreSSN)).thenReturn(belowLimitCreditScore);
		Mockito.when(agency.getScore(aboveLimitCreditScoreSSN)).thenReturn(aboveLimitCreditScore);
		
		//This line is required in order for the @Mock annotation to work.
		MockitoAnnotations.initMocks(this);

		agent.setCreditAgency(agency);
		agent.setErrorLog(errorLog);
	}
	
	//Not really needed as the JVM garbage collector would clean this up for us.  The teardown() function is here only to demo how @After works.
	@After
	public void teardown()  {
		agent = null;
		agency = null;
	}
	
	@Test
	public void testCreation() {
		Assert.assertNotNull(agent);
	}
	
	@Test
	public void testWithLowCreditScore()  {
		boolean result = agent.processLoan(lowCreditScoreSSN);
		Assert.assertFalse(result);	
	}
	
	@Test
	public void testWithBorderLineCreditScore()  {
		boolean result = agent.processLoan(borderLineCreditScoreSSN);
		Assert.assertTrue(result);	
	}
	
	@Test
	public void testWithGoodCreditScore()  {
		boolean result = agent.processLoan(goodCreditScoreSSN);
		Assert.assertTrue(result);	
	}
	
	//Note the absence of the assert statement.  We are verifying a method was called, not asserting a condition to be true
	@Test
	public void testWithbelowLimitCreditScore()  {
		boolean result = agent.processLoan(belowLimitCreditScoreSSN);
		Mockito.verify(errorLog, times(1)).recordErrorMessage(Mockito.anyString());	
		
	}
	
	//Note the absence of the assert statement.  We are verifying a method was called, not asserting a condition to be true
	@Test
	public void testWithAboveLimitCreditScore()  {
		boolean result = agent.processLoan(aboveLimitCreditScoreSSN);
		Mockito.verify(errorLog, times(1)).recordErrorMessage(Mockito.anyString());
		// To Do:  Check to insure that the exception for receiving a low credit score is thrown
	}
}
