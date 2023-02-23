package test.java;

import prodCode.CreditAgency;

public class CreditAgencyTestingStub implements CreditAgency {
	
	int creditScore = 0;
	
	public void setCreditScore(int score)  {
		creditScore = score;
	}
	
	public int getScore(String ssn)  {
		
		return creditScore;
		
	}

}
