package test;

import static org.mockito.Mockito.times;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import prodCode.CreditAgency;
import prodCode.ErrorLog;
import prodCode.LoanAgent;

public class LoanProcessingTests {
	
	LoanAgent agent;
	CreditAgency agency;
	//ErrorLog errorLog;
	
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
	
	@Mock
	ErrorLog errorLog;
	
	@Before
	public void setup()  {
		agent = new LoanAgent();
		agency = Mockito.mock(CreditAgency.class);
		Mockito.when(agency.getScore(lowCreditScoreSSN)).thenReturn(lowCreditScore);
		Mockito.when(agency.getScore(goodCreditScoreSSN)).thenReturn(goodCreditScore);
		Mockito.when(agency.getScore(borderLineCreditScoreSSN)).thenReturn(borderLineCreditScore);
		Mockito.when(agency.getScore(belowLimitCreditScoreSSN)).thenReturn(belowLimitCreditScore);
		Mockito.when(agency.getScore(aboveLimitCreditScoreSSN)).thenReturn(aboveLimitCreditScore);
		
		//errorLog = Mockito.mock(ErrorLog.class);
		MockitoAnnotations.initMocks(this);

		
		agent.setCreditAgency(agency);
		agent.setErrorLog(errorLog);
	}
	
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
	
	@Test
	public void testWithbelowLimitCreditScore()  {
		boolean result = agent.processLoan(belowLimitCreditScoreSSN);
		Mockito.verify(errorLog, times(1)).recordErrorMessage(Mockito.anyString());
		Assert.assertFalse(result);	
	}
	
	@Test
	public void testWithAboveLimitCreditScore()  {
		boolean result = agent.processLoan(aboveLimitCreditScoreSSN);
		Mockito.verify(errorLog, times(1)).recordErrorMessage(Mockito.anyString());
		Assert.assertTrue(result);	
	}
}
