package prodCode;

public class LoanAgent {
	
	CreditAgency agency;
	ErrorLog log;
	
	public void setCreditAgency(CreditAgency agency)  {
		this.agency = agency;
	}
	
	public boolean processLoan(String ssn)	 {	
		boolean result = false;
	
		int creditScore = agency.getScore(ssn);
	
		if(creditScore >= 720)  {
			result = true;
		}
		
		if (creditScore < 200 || creditScore > 850)  {
		log.recordErrorMessage(creditScore + " is an invalid score");	
		
		}
		
	return result;	
	}

	public void setErrorLog(ErrorLog errorLog) {
		this.log = errorLog;
		
	}
}

